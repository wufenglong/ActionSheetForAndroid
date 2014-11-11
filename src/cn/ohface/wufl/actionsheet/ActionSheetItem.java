package cn.ohface.wufl.actionsheet;
/**
 * actionsheet 内容
 *
 * @author wufl
 * @version 1.2014.11.11 初始创建。
 * @since 2014.11.11
 */
public class ActionSheetItem {
    public String content;
    public ActionSheetStyle type;

    public ActionSheetItem(String content, ActionSheetStyle sheetStyle) {
        this.content = content;
        this.type = sheetStyle;
    }
}