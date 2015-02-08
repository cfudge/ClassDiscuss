package com.example.cmput401.classdiscuss;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends sideBarMenuActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button enterButton = (Button) findViewById(R.id.enter);
        enterButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                sendMessage(v);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_side_bar_menu, menu);
        return true;
    }


    public void sendMessage(View view){
        Intent intent = new Intent(this, MyChannelScreen.class);

        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
