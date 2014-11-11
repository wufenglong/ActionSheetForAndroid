package cn.ohface.wufl.actionsheet;

import android.app.Dialog;
import android.content.Context;
import android.view.*;
import android.view.WindowManager.LayoutParams;
import android.widget.*;

import java.util.ArrayList;

/**
 * 仿ios选择列表对话框
 *
 * @author wufl
 * @version 1.2014.11.10 初始创建。
 * @since 2014.11.10
 */
public class ActionSheetDialog extends Dialog {
    public interface OnActionSheetItemClickListener {
        public void onItemClick(int clickIndex);
    }

    public interface OnActionSheetCancelClickListener {
        public void onCancelClick();
    }

    public ActionSheetDialog(Context context, int theme) {
        super(context, theme);
    }

    public ActionSheetDialog(Context context) {
        super(context);
    }

    @Override
    public void show() {
        super.show();
        Window window = getWindow();
        LayoutParams lp = window.getAttributes();
        lp.x = 0;
        lp.gravity = Gravity.BOTTOM;
        window.setAttributes(lp);
    }

    public static class Builder {
        private Context context;
        private CharSequence title;
        private boolean isHasCancel = true;
        private int cancelMarginTop = -1;
        private int contentDividerHeight = -1;
        private ActionSheetStyle titleSheetStyle = ActionSheetStyle.normal;
        private ActionSheetStyle cancelSheetStyle = ActionSheetStyle.normal;
        private OnActionSheetItemClickListener itemClickListener;
        private OnActionSheetCancelClickListener cancelListener;
        private ArrayList<ActionSheetItem> choices = null;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setTitle(int titleId) {
            this.title = (String) context.getText(titleId);
            return this;
        }

        public Builder setHasCancel(boolean isHasCancel) {
            this.isHasCancel = isHasCancel;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setTitleStyle(ActionSheetStyle sheetStyle) {
            this.titleSheetStyle = sheetStyle;
            return this;
        }

        public Builder setCancelStyle(ActionSheetStyle sheetStyle) {
            this.cancelSheetStyle = sheetStyle;
            return this;
        }

        public Builder setChoiceList(ArrayList<ActionSheetItem> choices) {
            this.choices = choices;
            return this;
        }

        public Builder setContentDividerHeight(int contentDividerHeight) {
            this.contentDividerHeight = contentDividerHeight;
            return this;
        }

        public Builder setCancelMarginTop(int marginTop) {
            cancelMarginTop = marginTop;
            return this;
        }

        public Builder setOnItemClickListener(OnActionSheetItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
            return this;
        }

        public Builder setOnCancelClickListener(OnActionSheetCancelClickListener cancelListener) {
            this.cancelListener = cancelListener;
            return this;
        }

        public ActionSheetDialog create() {
            final ActionSheetDialog dialog = new ActionSheetDialog(context, R.style.ActionSheet);
            // inflat layout
            LinearLayout layout = (LinearLayout) LayoutInflater.from(context)
                    .inflate(R.layout.action_sheet_layout, null);
            // set title
            TextView titleTv = (TextView) layout.findViewById(R.id.title);
            if (title != null) {
                titleTv.setText(title);
                setBgAndTextColor(titleTv, titleSheetStyle);
            } else {
                titleTv.setVisibility(View.GONE);
            }

            // set choices
            ListView listView = (ListView) layout.findViewById(R.id.sheetList);
            if (choices != null) {
                listView.setAdapter(new ActionSheetAdapter());
                if (contentDividerHeight != -1) {
                    listView.setDividerHeight(contentDividerHeight);
                }
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        itemClickListener.onItemClick(position);
                        dialog.dismiss();
                    }
                });
            }
            TextView tv_cancel = (TextView) layout.findViewById(R.id.cancel);
            if (isHasCancel) {
                tv_cancel.setVisibility(View.VISIBLE);
                setBgAndTextColor(tv_cancel, cancelSheetStyle);
                if (cancelMarginTop != -1) {
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0, cancelMarginTop, 0, 0);
                    tv_cancel.setLayoutParams(layoutParams);
                }
                // set cancel
                if (cancelListener != null) {
                    tv_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cancelListener.onCancelClick();
                            dialog.dismiss();
                        }
                    });
                } else {
                    tv_cancel.setOnClickListener(new View.OnClickListener() {
                                                     @Override
                                                     public void onClick(View v) {
                                                         dialog.dismiss();
                                                     }
                                                 }
                    );
                }
            } else {
                tv_cancel.setVisibility(View.GONE);
            }

            // set window param
            layout.setMinimumWidth(context.getResources().getDisplayMetrics().widthPixels);

            dialog.setCanceledOnTouchOutside(true);
            dialog.setContentView(layout);
            return dialog;
        }

        public ActionSheetDialog show() {
            ActionSheetDialog dialog = create();
            dialog.show();
            return dialog;
        }

        private void setBgAndTextColor(TextView tv, ActionSheetStyle sheetStyle) {
            if (ActionSheetStyle.blocker == sheetStyle) {
                tv.setTextColor(context.getResources().getColor(R.color.acitonsheet_item_text_blocker));
                tv.setBackgroundColor(context.getResources().getColor(R.color.acitonsheet_item_bg_blocker));
            } else if (ActionSheetStyle.critical == sheetStyle) {
                tv.setTextColor(context.getResources().getColor(R.color.acitonsheet_item_text_critical));
                tv.setBackgroundColor(context.getResources().getColor(R.color.acitonsheet_item_bg_critical));
            } else if (ActionSheetStyle.major == sheetStyle) {
                tv.setTextColor(context.getResources().getColor(R.color.acitonsheet_item_text_major));
                tv.setBackgroundColor(context.getResources().getColor(R.color.acitonsheet_item_bg_major));
            } else if (ActionSheetStyle.minor == sheetStyle) {
                tv.setTextColor(context.getResources().getColor(R.color.acitonsheet_item_text_minor));
                tv.setBackgroundColor(context.getResources().getColor(R.color.acitonsheet_item_bg_minor));
            } else {//normal
                tv.setTextColor(context.getResources().getColor(R.color.acitonsheet_item_text_normal));
                tv.setBackgroundColor(context.getResources().getColor(R.color.acitonsheet_item_bg_normal));
            }
        }

        class ActionSheetAdapter extends BaseAdapter {

            public ActionSheetAdapter() {
            }

            @Override
            public int getCount() {
                if (choices == null) {
                    return 0;
                }
                return choices.size();
            }

            @Override
            public Object getItem(int position) {
                if (choices == null) {
                    return null;
                }
                return choices.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder;
                if (convertView == null) {
                    holder = new ViewHolder();
                    convertView = LayoutInflater.from(context).inflate(R.layout.action_sheet_item, null);
                    holder.content = (TextView) convertView.findViewById(R.id.content);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                ActionSheetItem item = choices.get(position);

                holder.content.setText(item.content);
                setBgAndTextColor(holder.content, item.type);
                return convertView;
            }

            class ViewHolder {
                TextView content;
            }
        }
    }


}
