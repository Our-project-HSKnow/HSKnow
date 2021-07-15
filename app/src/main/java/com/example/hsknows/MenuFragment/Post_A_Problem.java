package com.example.hsknows.MenuFragment;
/**
 * 这个是发布问题的界面，包含标题，内容
 * 待开发：上传照片
 */

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class Post_A_Problem extends AppCompatActivity {
    TextView majorTitle;//主標題，顯示在學科名稱的位置上
    Button ButtonOfUpload;//上傳按鈕
    EditText EditTitle;//標題的EditText框
    EditText EditContent;//內容的EditText框


    //Button testsjk;//測試數據庫


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_a_question);
        /*接收上一個活動傳遞進來的學科名字，並打印在標題的textview的位置*/
        Intent intent1=getIntent();
        String SubjName=intent1.getStringExtra("SubjName");
        majorTitle = (TextView)findViewById(R.id.ShowSubject);
        majorTitle.setText(SubjName);//輸入標題（即學科名稱）

        //找到標題和內容的EditText
        EditTitle=(EditText)findViewById(R.id.ThisIsTitle);
        EditContent=(EditText)findViewById(R.id.ThisIsText);

        ButtonOfUpload=(Button)findViewById(R.id.Upload);
        ButtonOfUpload.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //如果點擊了上傳按鈕
                String title=EditTitle.getText().toString();
                String content=EditContent.getText().toString();
                if(TextUtils.isEmpty(title) || TextUtils.isEmpty(content)){
                    //如果標題與內容至少有一個為空，則會認為不能上傳
                    Toast.makeText(Post_A_Problem.this,"标题或正文不能为空！",Toast.LENGTH_SHORT).show();
                }else{

                    Intent intent1=new Intent();
                    intent1.putExtra("BackTitle",title);
                    intent1.putExtra("BackContent",content);
                    setResult(RESULT_OK,intent1);

                    finish();
                }


            }
        });

/*
        testsjk=(Button)findViewById(R.id.ceshileancloud);
        testsjk.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent1=new Intent(Post_A_Problem.this, MyLeanCloud.class);
                startActivity(intent1);
            }
        });
*/

    }
}