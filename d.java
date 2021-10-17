package androidx.recyclerview.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.view.MotionEvent;
import androidx.core.f.r;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.common.ConnectionResult;

class d extends RecyclerView.h implements RecyclerView.l {
    private static final int[] k = {16842919};
    private static final int[] l = new int[0];
    private int A = 0;
    private final int[] B = new int[2];
    private final int[] C = new int[2];
    private final Runnable D = new Runnable() {
        /* class androidx.recyclerview.widget.d.AnonymousClass1 */

        public void run() {
            d.this.b(500);
        }
    };
    private final RecyclerView.m E = new RecyclerView.m() {
        /* class androidx.recyclerview.widget.d.AnonymousClass2 */

        @Override // androidx.recyclerview.widget.RecyclerView.m
        public void a(RecyclerView recyclerView, int i, int i2) {
            d.this.a(recyclerView.computeHorizontalScrollOffset(), recyclerView.computeVerticalScrollOffset());
        }
    };
    final StateListDrawable a;
    final Drawable b;
    int c;
    int d;
    float e;
    int f;
    int g;
    float h;
    final ValueAnimator i = ValueAnimator.ofFloat(0.0f, 1.0f);
    int j = 0;
    private final int m;
    private final int n;
    private final int o;
    private final int p;
    private final StateListDrawable q;
    private final Drawable r;
    private final int s;
    private final int t;
    private int u = 0;
    private int v = 0;
    private RecyclerView w;
    private boolean x = false;
    private boolean y = false;
    private int z = 0;

    @Override // androidx.recyclerview.widget.RecyclerView.l
    public void a(boolean z2) {
    }

    d(RecyclerView recyclerView, StateListDrawable stateListDrawable, Drawable drawable, StateListDrawable stateListDrawable2, Drawable drawable2, int i2, int i3, int i4) {
        this.a = stateListDrawable;
        this.b = drawable;
        this.q = stateListDrawable2;
        this.r = drawable2;
        this.o = Math.max(i2, stateListDrawable.getIntrinsicWidth());
        this.p = Math.max(i2, drawable.getIntrinsicWidth());
        this.s = Math.max(i2, stateListDrawable2.getIntrinsicWidth());
        this.t = Math.max(i2, drawable2.getIntrinsicWidth());
        this.m = i3;
        this.n = i4;
        this.a.setAlpha(255);
        this.b.setAlpha(255);
        this.i.addListener(new a());
        this.i.addUpdateListener(new b());
        a(recyclerView);
    }

    public void a(RecyclerView recyclerView) {
        RecyclerView recyclerView2 = this.w;
        if (recyclerView2 != recyclerView) {
            if (recyclerView2 != null) {
                d();
            }
            this.w = recyclerView;
            if (this.w != null) {
                c();
            }
        }
    }

    private void c() {
        this.w.addItemDecoration(this);
        this.w.addOnItemTouchListener(this);
        this.w.addOnScrollListener(this.E);
    }

    private void d() {
        this.w.removeItemDecoration(this);
        this.w.removeOnItemTouchListener(this);
        this.w.removeOnScrollListener(this.E);
        f();
    }

    /* access modifiers changed from: package-private */
    public void a() {
        this.w.invalidate();
    }

    /* access modifiers changed from: package-private */
    public void a(int i2) {
        if (i2 == 2 && this.z != 2) {
            this.a.setState(k);
            f();
        }
        if (i2 == 0) {
            a();
        } else {
            b();
        }
        if (this.z == 2 && i2 != 2) {
            this.a.setState(l);
            c(1200);
        } else if (i2 == 1) {
            c(ConnectionResult.DRIVE_EXTERNAL_STORAGE_REQUIRED);
        }
        this.z = i2;
    }

    private boolean e() {
        return r.f(this.w) == 1;
    }

    public void b() {
        int i2 = this.j;
        if (i2 != 0) {
            if (i2 == 3) {
                this.i.cancel();
            } else {
                return;
            }
        }
        this.j = 1;
        ValueAnimator valueAnimator = this.i;
        valueAnimator.setFloatValues(((Float) valueAnimator.getAnimatedValue()).floatValue(), 1.0f);
        this.i.setDuration(500L);
        this.i.setStartDelay(0);
        this.i.start();
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    /* access modifiers changed from: package-private */
    public void b(int i2) {
        switch (this.j) {
            case 1:
                this.i.cancel();
                break;
            case 2:
                break;
            default:
                return;
        }
        this.j = 3;
        ValueAnimator valueAnimator = this.i;
        valueAnimator.setFloatValues(((Float) valueAnimator.getAnimatedValue()).floatValue(), 0.0f);
        this.i.setDuration((long) i2);
        this.i.start();
    }

    private void f() {
        this.w.removeCallbacks(this.D);
    }

    private void c(int i2) {
        f();
        this.w.postDelayed(this.D, (long) i2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.h
    public void a(Canvas canvas, RecyclerView recyclerView, RecyclerView.s sVar) {
        if (this.u != this.w.getWidth() || this.v != this.w.getHeight()) {
            this.u = this.w.getWidth();
            this.v = this.w.getHeight();
            a(0);
        } else if (this.j != 0) {
            if (this.x) {
                a(canvas);
            }
            if (this.y) {
                b(canvas);
            }
        }
    }

    private void a(Canvas canvas) {
        int i2 = this.u;
        int i3 = this.o;
        int i4 = i2 - i3;
        int i5 = this.d;
        int i6 = this.c;
        int i7 = i5 - (i6 / 2);
        this.a.setBounds(0, 0, i3, i6);
        this.b.setBounds(0, 0, this.p, this.v);
        if (e()) {
            this.b.draw(canvas);
            canvas.translate((float) this.o, (float) i7);
            canvas.scale(-1.0f, 1.0f);
            this.a.draw(canvas);
            canvas.scale(1.0f, 1.0f);
            canvas.translate((float) (-this.o), (float) (-i7));
            return;
        }
        canvas.translate((float) i4, 0.0f);
        this.b.draw(canvas);
        canvas.translate(0.0f, (float) i7);
        this.a.draw(canvas);
        canvas.translate((float) (-i4), (float) (-i7));
    }

    private void b(Canvas canvas) {
        int i2 = this.v;
        int i3 = this.s;
        int i4 = i2 - i3;
        int i5 = this.g;
        int i6 = this.f;
        int i7 = i5 - (i6 / 2);
        this.q.setBounds(0, 0, i6, i3);
        this.r.setBounds(0, 0, this.u, this.t);
        canvas.translate(0.0f, (float) i4);
        this.r.draw(canvas);
        canvas.translate((float) i7, 0.0f);
        this.q.draw(canvas);
        canvas.translate((float) (-i7), (float) (-i4));
    }

    /* access modifiers changed from: package-private */
    public void a(int i2, int i3) {
        int computeVerticalScrollRange = this.w.computeVerticalScrollRange();
        int i4 = this.v;
        this.x = computeVerticalScrollRange - i4 > 0 && i4 >= this.m;
        int computeHorizontalScrollRange = this.w.computeHorizontalScrollRange();
        int i5 = this.u;
        this.y = computeHorizontalScrollRange - i5 > 0 && i5 >= this.m;
        if (this.x || this.y) {
            if (this.x) {
                float f2 = (float) i4;
                this.d = (int) ((f2 * (((float) i3) + (f2 / 2.0f))) / ((float) computeVerticalScrollRange));
                this.c = Math.min(i4, (i4 * i4) / computeVerticalScrollRange);
            }
            if (this.y) {
                float f3 = (float) i5;
                this.g = (int) ((f3 * (((float) i2) + (f3 / 2.0f))) / ((float) computeHorizontalScrollRange));
                this.f = Math.min(i5, (i5 * i5) / computeHorizontalScrollRange);
            }
            int i6 = this.z;
            if (i6 == 0 || i6 == 1) {
                a(1);
            }
        } else if (this.z != 0) {
            a(0);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.l
    public boolean a(RecyclerView recyclerView, MotionEvent motionEvent) {
        int i2 = this.z;
        if (i2 == 1) {
            boolean a2 = a(motionEvent.getX(), motionEvent.getY());
            boolean b2 = b(motionEvent.getX(), motionEvent.getY());
            if (motionEvent.getAction() != 0) {
                return false;
            }
            if (!a2 && !b2) {
                return false;
            }
            if (b2) {
                this.A = 1;
                this.h = (float) ((int) motionEvent.getX());
            } else if (a2) {
                this.A = 2;
                this.e = (float) ((int) motionEvent.getY());
            }
            a(2);
            return true;
        } else if (i2 == 2) {
            return true;
        } else {
            return false;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.l
    public void b(RecyclerView recyclerView, MotionEvent motionEvent) {
        if (this.z != 0) {
            if (motionEvent.getAction() == 0) {
                boolean a2 = a(motionEvent.getX(), motionEvent.getY());
                boolean b2 = b(motionEvent.getX(), motionEvent.getY());
                if (a2 || b2) {
                    if (b2) {
                        this.A = 1;
                        this.h = (float) ((int) motionEvent.getX());
                    } else if (a2) {
                        this.A = 2;
                        this.e = (float) ((int) motionEvent.getY());
                    }
                    a(2);
                }
            } else if (motionEvent.getAction() == 1 && this.z == 2) {
                this.e = 0.0f;
                this.h = 0.0f;
                a(1);
                this.A = 0;
            } else if (motionEvent.getAction() == 2 && this.z == 2) {
                b();
                if (this.A == 1) {
                    b(motionEvent.getX());
                }
                if (this.A == 2) {
                    a(motionEvent.getY());
                }
            }
        }
    }

    private void a(float f2) {
        int[] g2 = g();
        float max = Math.max((float) g2[0], Math.min((float) g2[1], f2));
        if (Math.abs(((float) this.d) - max) >= 2.0f) {
            int a2 = a(this.e, max, g2, this.w.computeVerticalScrollRange(), this.w.computeVerticalScrollOffset(), this.v);
            if (a2 != 0) {
                this.w.scrollBy(0, a2);
            }
            this.e = max;
        }
    }

    private void b(float f2) {
        int[] h2 = h();
        float max = Math.max((float) h2[0], Math.min((float) h2[1], f2));
        if (Math.abs(((float) this.g) - max) >= 2.0f) {
            int a2 = a(this.h, max, h2, this.w.computeHorizontalScrollRange(), this.w.computeHorizontalScrollOffset(), this.u);
            if (a2 != 0) {
                this.w.scrollBy(a2, 0);
            }
            this.h = max;
        }
    }

    private int a(float f2, float f3, int[] iArr, int i2, int i3, int i4) {
        int i5 = iArr[1] - iArr[0];
        if (i5 == 0) {
            return 0;
        }
        int i6 = i2 - i4;
        int i7 = (int) (((f3 - f2) / ((float) i5)) * ((float) i6));
        int i8 = i3 + i7;
        if (i8 >= i6 || i8 < 0) {
            return 0;
        }
        return i7;
    }

    /* access modifiers changed from: package-private */
    public boolean a(float f2, float f3) {
        if (!e() ? f2 >= ((float) (this.u - this.o)) : f2 <= ((float) (this.o / 2))) {
            int i2 = this.d;
            int i3 = this.c;
            return f3 >= ((float) (i2 - (i3 / 2))) && f3 <= ((float) (i2 + (i3 / 2)));
        }
    }

    /* access modifiers changed from: package-private */
    public boolean b(float f2, float f3) {
        if (f3 >= ((float) (this.v - this.s))) {
            int i2 = this.g;
            int i3 = this.f;
            return f2 >= ((float) (i2 - (i3 / 2))) && f2 <= ((float) (i2 + (i3 / 2)));
        }
    }

    private int[] g() {
        int[] iArr = this.B;
        int i2 = this.n;
        iArr[0] = i2;
        iArr[1] = this.v - i2;
        return iArr;
    }

    private int[] h() {
        int[] iArr = this.C;
        int i2 = this.n;
        iArr[0] = i2;
        iArr[1] = this.u - i2;
        return iArr;
    }

    private class a extends AnimatorListenerAdapter {
        private boolean b = false;

        a() {
        }

        public void onAnimationEnd(Animator animator) {
            if (this.b) {
                this.b = false;
            } else if (((Float) d.this.i.getAnimatedValue()).floatValue() == 0.0f) {
                d dVar = d.this;
                dVar.j = 0;
                dVar.a(0);
            } else {
                d dVar2 = d.this;
                dVar2.j = 2;
                dVar2.a();
            }
        }

        public void onAnimationCancel(Animator animator) {
            this.b = true;
        }
    }

    private class b implements ValueAnimator.AnimatorUpdateListener {
        b() {
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            int floatValue = (int) (((Float) valueAnimator.getAnimatedValue()).floatValue() * 255.0f);
            d.this.a.setAlpha(floatValue);
            d.this.b.setAlpha(floatValue);
            d.this.a();
        }
    }
}
