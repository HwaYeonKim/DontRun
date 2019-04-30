package com.example.administrator.dontrun;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

/**
 * Created by Administrator on 2015-12-24.
 */
public class MenuDialog extends Dialog{
    Context mcontext;
    ImageView menu1, menu2;

    public MenuDialog(Context context) {
        super(context);
        this.mcontext = context;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_menu);

        menu1 = (ImageView)findViewById(R.id.menu1_btn);
        menu2 = (ImageView)findViewById(R.id.menu2_btn);

        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 도움말 없음.
                dismiss();
            }
        });

        menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UserSetActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getContext().startActivity(intent);

                dismiss();
            }
        });
    }
}
