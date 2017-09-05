package cn.wingene.mallx.frame.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import junze.android.text.mothed.InputConfig;
import junze.android.ui.ViewHolder;
import junze.android.util.DialogUtil;
import junze.android.util.EditTextUtil;
import junze.androidxf.kit.AKit;

import cn.wingene.mall.R;

public class EditViewDialogDeclare {
    public interface OnEditCompleteListener {
        public void onEditComplete(String record);
    }

    public static abstract class OnClickListener implements DialogInterface.OnClickListener {
        private EditViewDialog mDialog;

        public void bindEditViewDialog(EditViewDialog dialog) {
            this.mDialog = dialog;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            onClick(mDialog, which);
        }

        abstract public void onClick(EditViewDialog dialog, int which);

    }

    public static class Option {
        public String initial;
        public String btn1;
        public OnClickListener l1;
        public String btn2;
        public OnClickListener l2;
        /**
         * support null
         */
        public Integer rawInputType;
        public Integer inputType;

        public static Option createOption(String initial, String btn1, OnClickListener l1, String btn2,
                OnClickListener l2) {
            Option option = new Option();
            option.initial = initial;
            option.btn1 = btn1;
            option.l1 = l1;
            option.btn2 = btn2;
            option.l2 = l2;
            return option;
        }

        public static Option createOption(int rawInputType) {
            Option option = createOption();
            option.rawInputType = rawInputType;
            return option;
        }

//        public static Option createOption(int inputType,int rawInputType) {
//            Option option = createOption();
//            option.inputType = inputType;
//            option.rawInputType = rawInputType;
//            return option;
//        }

        public static Option createOption() {
            return createOption("", "取消 ", new OnClickListener() {
                @Override
                public void onClick(EditViewDialog dialog, int which) {
                    dialog.hideOnCancel();
                }
            }, "确定", new OnClickListener() {

                @Override
                public void onClick(EditViewDialog dialog, int which) {
                    dialog.onEditComplete();
                    dialog.hideOnSuccess();

                }
            });
        }
    }

    public static class EditViewDialog extends ViewHolder {
//        private final Activity mActivity;
        private AlertDialog mDialog;
        private EditText mEditText;
        private OnEditCompleteListener mOnEditComplate;
        private String mInitial;

        public EditViewDialog(Context context) {
            super(context, R.layout.dialog_number);
        }

        //        public EditViewDialog(Activity activity) {
//            this.mActivity = activity;
//        }
//
//        public EditViewDialog(Activity activity, EditText editText) {
//            this.mActivity = activity;
//            mEditText = editText;
//            _initEditText();
//        }

        public void initEditText() {
            if (mEditText == null) {
                mEditText = new EditText(mContext);
                _initEditText();
            }
        }

        private void _initEditText() {
            mEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String content = mEditText.getText().toString();
                    if (content.contains("\r") || content.contains("\n")) {
                        onEditComplete();
                    }

                }
            });
            mEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
            mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    onEditComplete();
                    return true;
                }

            });
        }

        public void onEditComplete() {
            mDialog.cancel();
            AKit.hideSoftInput(getActivity());
            if (mOnEditComplate != null) {
                mOnEditComplate.onEditComplete(mEditText.getText().toString());
            }
        }

        public void hideOnCancel(){
            mDialog.cancel();
            AKit.hideSoftInput(getActivity());
            mDialog.show();
        }
        public void hideOnSuccess(){
            mDialog.show();
        }

        public void initDialog() {
            if (mDialog == null) {
                initEditText();
                mDialog = new AlertDialog.Builder(getActivity()).setView(mEditText).create();
                mDialog.setOnShowListener(new DialogInterface.OnShowListener() {

                    @Override
                    public void onShow(DialogInterface dialog) {
                        mEditText.setText(mInitial);
                        EditTextUtil.moveCourseToLast(mEditText);
                        InputMethodManager imm = (InputMethodManager) getActivity()
                                .getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                });
                mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        mEditText.postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                AKit.hideSoftInputFromWindow(getActivity());
                            };
                        }, 200);
                    }
                });
            }
        }

        public AlertDialog getDialog() {
            return mDialog;
        }

        public EditViewDialog setRawInputType(int type) {
            initEditText();
            if(InputConfig.getInstance().isRawInputable()){
                mEditText.setRawInputType(type);
            }
            return this;
        }

        public EditViewDialog setParams(CharSequence title, final OnEditCompleteListener onEditComplete, Option o) {
            this.mOnEditComplate = onEditComplete;
            initDialog();
            mDialog.setTitle(title);
            if (o.l1 != null) {
                o.l1.bindEditViewDialog(this);
            }
            if (o.l2 != null) {
                o.l2.bindEditViewDialog(this);
            }
            DialogUtil.setDialogButton(mDialog, DialogInterface.BUTTON_NEUTRAL, o.btn1, o.l1);
            DialogUtil.setDialogButton(mDialog, DialogInterface.BUTTON_NEGATIVE, o.btn2, o.l2);
            this.mInitial = o.initial != null ? o.initial : "";
            if(o.inputType != null){
                mEditText.setInputType(o.inputType);
            }
            if (o.rawInputType != null && InputConfig.getInstance().isRawInputable()) {
                mEditText.setRawInputType(o.rawInputType);
            }
            return this;
        }

        @Override
        protected void initComponent() {

        }
    }
}
