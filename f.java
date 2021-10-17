package androidx.recyclerview.widget;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import org.azeckoski.reflectutils.transcoders.JSONTranscoder;

class f {
    boolean a = true;
    int b;
    int c;
    int d;
    int e;
    int f = 0;
    int g = 0;
    boolean h;
    boolean i;

    f() {
    }

    /* access modifiers changed from: package-private */
    public boolean a(RecyclerView.s sVar) {
        int i2 = this.c;
        return i2 >= 0 && i2 < sVar.e();
    }

    /* access modifiers changed from: package-private */
    public View a(RecyclerView.o oVar) {
        View c2 = oVar.c(this.c);
        this.c += this.d;
        return c2;
    }

    public String toString() {
        return "LayoutState{mAvailable=" + this.b + ", mCurrentPosition=" + this.c + ", mItemDirection=" + this.d + ", mLayoutDirection=" + this.e + ", mStartLine=" + this.f + ", mEndLine=" + this.g + JSONTranscoder.OBJ_END;
    }
}
