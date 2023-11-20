package lab5.modules.shape_editor;

import android.view.MotionEvent;
import androidx.annotation.NonNull;

import lab5.modules.CallBack;
import lab5.modules.shape_editor.shapes.Shape;

public class MyEditorSingleton implements MyEditorInterface {

    private CallBack callback;
    private MyEditor myView;
    private static class SingletonHolder {
        private static final MyEditorSingleton INSTANCE = new MyEditorSingleton();
    }

    private MyEditorSingleton() {
    }

    public static MyEditorSingleton getInstance() {
        return SingletonHolder.INSTANCE;

    }
    public void initialize(MyEditor myView, CallBack callback) {
        if (this.myView == null && this.callback == null) {
            this.myView = myView;
            this.myView.myEditorContext = this;
            this.callback = callback;
        }
    }

    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public CallBack getCallback() {
        return callback;
    }

    public void setCallback(CallBack callback) {
        this.callback = callback;
    }

    public MyEditor getMyView() {
        return myView;
    }

    public void setMyView(MyEditor myView) {
        this.myView = myView;
    }

    @Override
    public void start(Shape shape) {

        myView.lastEdited = shape;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }
}
