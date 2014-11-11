package cn.ohface.wufl.actionsheet;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import java.util.ArrayList;

/**
 * 仿ios选择列表对话框
 *
 * @author wufl
 * @version 1.2014.11.10 初始创建。
 * @since 2014.11.10
 */
public class MainActivity extends Activity {
    ActionSheetDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final ArrayList<ActionSheetItem> items = new ArrayList<ActionSheetItem>();
        ActionSheetItem item1 = new ActionSheetItem("ddd", ActionSheetStyle.blocker);
        ActionSheetItem item2 = new ActionSheetItem("ddd", ActionSheetStyle.critical);
        ActionSheetItem item3 = new ActionSheetItem("ddd", ActionSheetStyle.major);
        ActionSheetItem item4 = new ActionSheetItem("ddd", ActionSheetStyle.normal);
        ActionSheetItem item5 = new ActionSheetItem("ddd", ActionSheetStyle.minor);
        items.add(item1);
        items.add(item2);
        items.add(item3);
        items.add(item4);
        items.add(item5);
        findViewById(R.id.btn).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog = new ActionSheetDialog.Builder(MainActivity.this)
                        .setTitle("title")
                        .setTitleStyle(ActionSheetStyle.major)
//                        .setCancelStyle(ActionSheetStyle.normal)
//                        .setHasCancel(false)
//                        .setContentDividerHeight(1)
                        .setCancelMarginTop(10)
                        .setChoiceList(items)
                        .setOnItemClickListener(new ActionSheetDialog.OnActionSheetItemClickListener() {
                            @Override
                            public void onItemClick(int clickIndex) {
                                Log.d("wufl", "onItemClick.......clickIndex=" + clickIndex);
                            }
                        }).setOnCancelClickListener(new ActionSheetDialog.OnActionSheetCancelClickListener() {
                            @Override
                            public void onCancelClick() {
                                Log.d("wufl", "onCancelClick.......");
                            }
                        }).show();
            }
        });
    }
}
