package com.owant.thinkmap.ui.editmap;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.owant.thinkmap.AppConstants;
import com.owant.thinkmap.R;
import com.owant.thinkmap.base.BaseActivity;
import com.owant.thinkmap.model.NodeModel;
import com.owant.thinkmap.model.TreeModel;
import com.owant.thinkmap.ui.EditAlertDialog;
import com.owant.thinkmap.util.AndroidUtil;
import com.owant.thinkmap.util.DensityUtils;
import com.owant.thinkmap.util.LOG;
import com.owant.thinkmap.view.RightTreeLayoutManager;
import com.owant.thinkmap.view.TreeView;
import com.owant.thinkmap.view.TreeViewItemClick;
import com.owant.thinkmap.view.TreeViewItemLongClick;

import java.io.Serializable;

/**
 * Created by owant on 21/03/2017.
 */

public class EditMapActivity extends BaseActivity implements EditMapContract.View {
    private final static String TAG = "EditMapActivity";
    private final static String tree_model = "tree_model";

    private String saveDefaultFilePath;
    private EditMapContract.Presenter mEditMapPresenter;

    private TreeView editMapTreeView;
    private Button btnAddSub;
    private Button btnAddNode;
    private Button btnFocusMid;
    private Button btnCodeMode;

    private EditAlertDialog addSubNodeDialog = null;
    private EditAlertDialog addNodeDialog = null;
    private EditAlertDialog editNodeDialog = null;
    private EditAlertDialog saveFileDialog = null;

    @Override
    protected void onBaseIntent() {

    }

    @Override
    protected void onBasePreLayout() {

    }

    @Override
    protected int onBaseLayoutId(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_edit_think_map;
    }

    public void bindViews() {

        editMapTreeView = (TreeView) findViewById(R.id.edit_map_tree_view);
        btnAddSub = (Button) findViewById(R.id.btn_add_sub);
        btnAddNode = (Button) findViewById(R.id.btn_add_node);
        btnFocusMid = (Button) findViewById(R.id.btn_focus_mid);
        btnCodeMode = (Button) findViewById(R.id.btn_code_mode);
    }

    @Override
    protected void onBaseBindView() {
        bindViews();

        btnAddNode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditMapPresenter.addNote();
            }
        });

        btnAddSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditMapPresenter.addSubNote();
            }
        });

        btnFocusMid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditMapPresenter.focusMid();
            }
        });

        btnCodeMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO ????????????????????????
                Toast.makeText(EditMapActivity.this,
                        "This feature is in development...",
                        Toast.LENGTH_SHORT).show();
            }
        });


        int dx = DensityUtils.dp2px(getApplicationContext(), 20);
        int dy = DensityUtils.dp2px(getApplicationContext(), 20);
        int screenHeight = DensityUtils.dp2px(getApplicationContext(), 720);
        editMapTreeView.setTreeLayoutManager(new RightTreeLayoutManager(dx, dy, screenHeight));

        editMapTreeView.setTreeViewItemLongClick(new TreeViewItemLongClick() {
            @Override
            public void onLongClick(View view) {
                mEditMapPresenter.editNote();
            }
        });

        editMapTreeView.setTreeViewItemClick(new TreeViewItemClick() {
            @Override
            public void onItemClick(View item) {

            }
        });

        initPresenter();
        //TODO ?????????????????????????????????
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(tree_model, mEditMapPresenter.getTreeModel());
        Log.i(TAG, "onSaveInstanceState: ????????????");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Serializable saveZable = savedInstanceState.getSerializable(tree_model);
        mEditMapPresenter.setTreeModel((TreeModel<String>) saveZable);
    }

    private void initPresenter() {
        //presenter????????????View
        mEditMapPresenter = new EditMapPresenter(this);
        mEditMapPresenter.start();

        Intent intent = getIntent();
        Uri data = intent.getData();
        if (data != null) {
            final String path = data.getPath();
            //??????owant???????????????
            presenterSetLoadMapPath(path);
            //??????owant??????
            mEditMapPresenter.readOwantFile();
        } else {
            mEditMapPresenter.createDefaultTreeModel();
        }
    }

    private void presenterSetLoadMapPath(String path) {
        mEditMapPresenter.setLoadMapPath(path);
    }

    @Override
    protected void onLoadData() {
    }

    @Override
    public void setPresenter(EditMapContract.Presenter presenter) {
        if (mEditMapPresenter == null) {
            mEditMapPresenter = presenter;
        }
    }

    @Override
    public void showLoadingFile() {

    }

    @Override
    public void setTreeViewData(TreeModel<String> treeModel) {
        editMapTreeView.setTreeModel(treeModel);
    }

    @Override
    public void hideLoadingFile() {

    }

    @Override
    public void showAddNoteDialog() {
        if (editMapTreeView.getCurrentFocusNode().getParentNode() == null) {
            Toast.makeText(this, getString(R.string.cannot_add_node), Toast.LENGTH_SHORT).show();
        } else if (addNodeDialog == null) {
            LayoutInflater factory = LayoutInflater.from(this);
            View inflate = factory.inflate(R.layout.dialog_edit_input, null);
            addNodeDialog = new EditAlertDialog(EditMapActivity.this);
            addNodeDialog.setView(inflate);
            addNodeDialog.setDivTitle(getString(R.string.add_a_same_floor_node));
            addNodeDialog.addEnterCallBack(new EditAlertDialog.EnterCallBack() {
                @Override
                public void onEdit(String value) {
                    if (TextUtils.isEmpty(value)) {
                        value = getString(R.string.null_node);
                    }
                    editMapTreeView.addNode(value);
                    clearDialog(addNodeDialog);
                    if (addNodeDialog != null && addNodeDialog.isShowing())
                        addNodeDialog.dismiss();
                }
            });
            addNodeDialog.show();
        } else {
            addNodeDialog.clearInput();
            addNodeDialog.show();
        }
    }

    @Override
    public void showAddSubNoteDialog() {
        if (addSubNodeDialog == null) {
            LayoutInflater factory = LayoutInflater.from(this);
            View inflate = factory.inflate(R.layout.dialog_edit_input, null);
            addSubNodeDialog = new EditAlertDialog(this);
            addSubNodeDialog.setView(inflate);
            addSubNodeDialog.setDivTitle(getString(R.string.add_a_sub_node));
            addSubNodeDialog.addEnterCallBack(new EditAlertDialog.EnterCallBack() {
                @Override
                public void onEdit(String value) {
                    if (TextUtils.isEmpty(value)) {
                        value = getString(R.string.null_node);
                    }
                    editMapTreeView.addSubNode(value);
                    clearDialog(addSubNodeDialog);
                }
            });
            addSubNodeDialog.show();
        } else {
            addSubNodeDialog.clearInput();
            addSubNodeDialog.show();
        }
    }

    @Override
    public void showEditNoteDialog() {
        if (editNodeDialog == null) {
            LayoutInflater factory = LayoutInflater.from(this);
            View view = factory.inflate(R.layout.dialog_edit_input, null);
            editNodeDialog = new EditAlertDialog(this);
            editNodeDialog.setView(view);
            editNodeDialog.setDivTitle(getString(R.string.edit_node));
        }

        editNodeDialog.setNodeModel(getCurrentFocusNode());
        editNodeDialog.setInput(getCurrentFocusNode().getValue());
        editNodeDialog.addDeleteCallBack(new EditAlertDialog.DeleteCallBack() {
            @Override
            public void onDeleteModel(NodeModel<String> model) {
                try {
                    editMapTreeView.deleteNode(model);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onDelete() {
            }
        });
        editNodeDialog.addEnterCallBack(new EditAlertDialog.EnterCallBack() {
            @Override
            public void onEdit(String value) {
                if (TextUtils.isEmpty(value)) {
                    value = getString(R.string.null_node);
                }
                editMapTreeView.changeNodeValue(getCurrentFocusNode(), value);
                clearDialog(editNodeDialog);
            }
        });
        editNodeDialog.show();
    }

    @Override
    public void showSaveFileDialog(String fileName) {
        if (saveFileDialog == null) {
            LayoutInflater factory = LayoutInflater.from(this);
            View view = factory.inflate(R.layout.dialog_edit_input, null);
            saveFileDialog = new EditAlertDialog(this);
            saveFileDialog.setView(view);
            saveFileDialog.setDivTitle(getString(R.string.save_file));
        }
        //???????????????????????????????????????????????????????????????????????????????????????
        saveFileDialog.setInput(mEditMapPresenter.getSaveInput());
        saveFileDialog.setConditionDeleteTextValue(getString(R.string.exit_edit));

        //???????????????????????????????????????????????????
        saveFileDialog.setCheckLists(mEditMapPresenter.getOwantLists());

        saveFileDialog.addEnterCallBack(new EditAlertDialog.EnterCallBack() {
            @Override
            public void onEdit(String value) {

                mEditMapPresenter.doSaveFile(value);

                //????????????
                clearDialog(saveFileDialog);
                EditMapActivity.this.finish();
            }
        });

        saveFileDialog.addDeleteCallBack(new EditAlertDialog.DeleteCallBack() {
            @Override
            public void onDeleteModel(NodeModel<String> nodeModel) {

            }

            @Override
            public void onDelete() {
                EditMapActivity.this.finish();
            }
        });
        saveFileDialog.show();
    }

    @Override
    public void focusingMid() {
        editMapTreeView.focusMidLocation();
    }

    @Override
    public String getDefaultPlanStr() {
        return getString(R.string.defualt_my_plan);
    }

    @Override
    public NodeModel<String> getCurrentFocusNode() {
        return editMapTreeView.getCurrentFocusNode();
    }

    @Override
    public String getDefaultSaveFilePath() {
        saveDefaultFilePath = Environment.getExternalStorageDirectory().getPath() + AppConstants.owant_maps;
        LOG.jLogi("saveDefaultFilePath=%s", saveDefaultFilePath);
        return saveDefaultFilePath;
    }

    @Override
    public String getAppVersion() {
        return AndroidUtil.getAppVersion(getApplicationContext());
    }

    @Override
    public void finishActivity() {
        EditMapActivity.this.finish();
    }

    private void clearDialog(Dialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //TODO ?????????????????????????????????
            mEditMapPresenter.saveFile();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        mEditMapPresenter.onRecycle();
        super.onDestroy();
    }
}
