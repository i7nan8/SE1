package androidx.recyclerview.widget;

import android.content.Context;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import androidx.recyclerview.widget.RecyclerView;
import com.trusteer.taz.c.c;

public class g extends RecyclerView.r {
    protected final LinearInterpolator a = new LinearInterpolator();
    protected final DecelerateInterpolator b = new DecelerateInterpolator();
    protected PointF c;
    protected int d = 0;
    protected int e = 0;
    private final float f;

    private int b(int i, int i2) {
        int i3 = i - i2;
        if (i * i3 <= 0) {
            return 0;
        }
        return i3;
    }

    /* access modifiers changed from: protected */
    @Override // androidx.recyclerview.widget.RecyclerView.r
    public void a() {
    }

    public g(Context context) {
        this.f = a(context.getResources().getDisplayMetrics());
    }

    /* access modifiers changed from: protected */
    @Override // androidx.recyclerview.widget.RecyclerView.r
    public void a(View view, RecyclerView.s sVar, RecyclerView.r.a aVar) {
        int b2 = b(view, c());
        int a2 = a(view, d());
        int a3 = a((int) Math.sqrt((double) ((b2 * b2) + (a2 * a2))));
        if (a3 > 0) {
            aVar.a(-b2, -a2, a3, this.b);
        }
    }

    /* access modifiers changed from: protected */
    @Override // androidx.recyclerview.widget.RecyclerView.r
    public void a(int i, int i2, RecyclerView.s sVar, RecyclerView.r.a aVar) {
        if (j() == 0) {
            f();
            return;
        }
        this.d = b(this.d, i);
        this.e = b(this.e, i2);
        if (this.d == 0 && this.e == 0) {
            a(aVar);
        }
    }

    /* access modifiers changed from: protected */
    @Override // androidx.recyclerview.widget.RecyclerView.r
    public void b() {
        this.e = 0;
        this.d = 0;
        this.c = null;
    }

    /* access modifiers changed from: protected */
    public float a(DisplayMetrics displayMetrics) {
        return 25.0f / ((float) displayMetrics.densityDpi);
    }

    /* access modifiers changed from: protected */
    public int a(int i) {
        double b2 = (double) b(i);
        Double.isNaN(b2);
        return (int) Math.ceil(b2 / 0.3356d);
    }

    /* access modifiers changed from: protected */
    public int b(int i) {
        return (int) Math.ceil((double) (((float) Math.abs(i)) * this.f));
    }

    /* access modifiers changed from: protected */
    public int c() {
        PointF pointF = this.c;
        if (pointF == null || pointF.x == 0.0f) {
            return 0;
        }
        return this.c.x > 0.0f ? 1 : -1;
    }

    /* access modifiers changed from: protected */
    public int d() {
        PointF pointF = this.c;
        if (pointF == null || pointF.y == 0.0f) {
            return 0;
        }
        return this.c.y > 0.0f ? 1 : -1;
    }

    /* access modifiers changed from: protected */
    public void a(RecyclerView.r.a aVar) {
        PointF d2 = d(i());
        if (d2 == null || (d2.x == 0.0f && d2.y == 0.0f)) {
            aVar.a(i());
            f();
            return;
        }
        a(d2);
        this.c = d2;
        this.d = (int) (d2.x * 10000.0f);
        this.e = (int) (d2.y * 10000.0f);
        aVar.a((int) (((float) this.d) * 1.2f), (int) (((float) this.e) * 1.2f), (int) (((float) b(c.m)) * 1.2f), this.a);
    }

    public int a(int i, int i2, int i3, int i4, int i5) {
        switch (i5) {
            case -1:
                return i3 - i;
            case 0:
                int i6 = i3 - i;
                if (i6 > 0) {
                    return i6;
                }
                int i7 = i4 - i2;
                if (i7 < 0) {
                    return i7;
                }
                return 0;
            case 1:
                return i4 - i2;
            default:
                throw new IllegalArgumentException("snap preference should be one of the constants defined in SmoothScroller, starting with SNAP_");
        }
    }

    public int a(View view, int i) {
        RecyclerView.i e2 = e();
        if (e2 == null || !e2.f()) {
            return 0;
        }
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        return a(e2.i(view) - layoutParams.topMargin, e2.k(view) + layoutParams.bottomMargin, e2.B(), e2.z() - e2.D(), i);
    }

    public int b(View view, int i) {
        RecyclerView.i e2 = e();
        if (e2 == null || !e2.e()) {
            return 0;
        }
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        return a(e2.h(view) - layoutParams.leftMargin, e2.j(view) + layoutParams.rightMargin, e2.A(), e2.y() - e2.C(), i);
    }
}
