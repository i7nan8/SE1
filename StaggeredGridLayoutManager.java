package androidx.recyclerview.widget;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import androidx.core.f.a.c;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.common.api.Api;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import org.azeckoski.reflectutils.transcoders.JSONTranscoder;

public class StaggeredGridLayoutManager extends RecyclerView.i implements RecyclerView.r.b {
    private SavedState A;
    private int B;
    private final Rect C = new Rect();
    private final a D = new a();
    private boolean E = false;
    private boolean F = true;
    private int[] G;
    private final Runnable H = new Runnable() {
        /* class androidx.recyclerview.widget.StaggeredGridLayoutManager.AnonymousClass1 */

        public void run() {
            StaggeredGridLayoutManager.this.g();
        }
    };
    b[] a;
    i b;
    i c;
    boolean d = false;
    boolean e = false;
    int f = -1;
    int g = androidx.customview.a.a.INVALID_ID;
    LazySpanLookup h = new LazySpanLookup();
    private int i = -1;
    private int j;
    private int k;
    private final f l;
    private BitSet m;
    private int n = 2;
    private boolean o;
    private boolean z;

    public StaggeredGridLayoutManager(Context context, AttributeSet attributeSet, int i2, int i3) {
        RecyclerView.i.b a2 = a(context, attributeSet, i2, i3);
        b(a2.a);
        a(a2.b);
        a(a2.c);
        this.l = new f();
        M();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public boolean c() {
        return this.n != 0;
    }

    private void M() {
        this.b = i.a(this, this.j);
        this.c = i.a(this, 1 - this.j);
    }

    /* access modifiers changed from: package-private */
    public boolean g() {
        int i2;
        int i3;
        if (v() == 0 || this.n == 0 || !q()) {
            return false;
        }
        if (this.e) {
            i3 = K();
            i2 = L();
        } else {
            i3 = L();
            i2 = K();
        }
        if (i3 == 0 && h() != null) {
            this.h.a();
            I();
            o();
            return true;
        } else if (!this.E) {
            return false;
        } else {
            int i4 = this.e ? -1 : 1;
            int i5 = i2 + 1;
            LazySpanLookup.FullSpanItem a2 = this.h.a(i3, i5, i4, true);
            if (a2 == null) {
                this.E = false;
                this.h.a(i5);
                return false;
            }
            LazySpanLookup.FullSpanItem a3 = this.h.a(i3, a2.a, i4 * -1, true);
            if (a3 == null) {
                this.h.a(a2.a);
            } else {
                this.h.a(a3.a + 1);
            }
            I();
            o();
            return true;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void l(int i2) {
        if (i2 == 0) {
            g();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void a(RecyclerView recyclerView, RecyclerView.o oVar) {
        super.a(recyclerView, oVar);
        a(this.H);
        for (int i2 = 0; i2 < this.i; i2++) {
            this.a[i2].e();
        }
        recyclerView.requestLayout();
    }

    /* access modifiers changed from: package-private */
    public View h() {
        int i2;
        int i3;
        boolean z2;
        int v = v() - 1;
        BitSet bitSet = new BitSet(this.i);
        bitSet.set(0, this.i, true);
        int i4 = -1;
        char c2 = (this.j != 1 || !j()) ? (char) 65535 : 1;
        if (this.e) {
            i2 = -1;
        } else {
            i2 = v + 1;
            v = 0;
        }
        if (v < i2) {
            i4 = 1;
        }
        while (v != i2) {
            View i5 = i(v);
            LayoutParams layoutParams = (LayoutParams) i5.getLayoutParams();
            if (bitSet.get(layoutParams.a.e)) {
                if (a(layoutParams.a)) {
                    return i5;
                }
                bitSet.clear(layoutParams.a.e);
            }
            if (!layoutParams.b && (i3 = v + i4) != i2) {
                View i6 = i(i3);
                if (this.e) {
                    int b2 = this.b.b(i5);
                    int b3 = this.b.b(i6);
                    if (b2 < b3) {
                        return i5;
                    }
                    z2 = b2 == b3;
                } else {
                    int a2 = this.b.a(i5);
                    int a3 = this.b.a(i6);
                    if (a2 > a3) {
                        return i5;
                    }
                    z2 = a2 == a3;
                }
                if (!z2) {
                    continue;
                } else {
                    if ((layoutParams.a.e - ((LayoutParams) i6.getLayoutParams()).a.e < 0) != (c2 < 0)) {
                        return i5;
                    }
                }
            }
            v += i4;
        }
        return null;
    }

    private boolean a(b bVar) {
        if (this.e) {
            if (bVar.d() < this.b.d()) {
                return !bVar.c(bVar.a.get(bVar.a.size() - 1)).b;
            }
        } else if (bVar.b() > this.b.c()) {
            return !bVar.c(bVar.a.get(0)).b;
        }
        return false;
    }

    public void a(int i2) {
        a((String) null);
        if (i2 != this.i) {
            i();
            this.i = i2;
            this.m = new BitSet(this.i);
            this.a = new b[this.i];
            for (int i3 = 0; i3 < this.i; i3++) {
                this.a[i3] = new b(i3);
            }
            o();
        }
    }

    public void b(int i2) {
        if (i2 == 0 || i2 == 1) {
            a((String) null);
            if (i2 != this.j) {
                this.j = i2;
                i iVar = this.b;
                this.b = this.c;
                this.c = iVar;
                o();
                return;
            }
            return;
        }
        throw new IllegalArgumentException("invalid orientation.");
    }

    public void a(boolean z2) {
        a((String) null);
        SavedState savedState = this.A;
        if (!(savedState == null || savedState.h == z2)) {
            this.A.h = z2;
        }
        this.d = z2;
        o();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void a(String str) {
        if (this.A == null) {
            super.a(str);
        }
    }

    public void i() {
        this.h.a();
        o();
    }

    private void N() {
        if (this.j == 1 || !j()) {
            this.e = this.d;
        } else {
            this.e = !this.d;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean j() {
        return t() == 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void a(Rect rect, int i2, int i3) {
        int i4;
        int i5;
        int A2 = A() + C();
        int B2 = B() + D();
        if (this.j == 1) {
            i5 = a(i3, rect.height() + B2, G());
            i4 = a(i2, (this.k * this.i) + A2, F());
        } else {
            i4 = a(i2, rect.width() + A2, F());
            i5 = a(i3, (this.k * this.i) + B2, G());
        }
        f(i4, i5);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void c(RecyclerView.o oVar, RecyclerView.s sVar) {
        a(oVar, sVar, true);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:83:0x0161, code lost:
        if (g() != false) goto L_0x0165;
     */
    private void a(RecyclerView.o oVar, RecyclerView.s sVar, boolean z2) {
        SavedState savedState;
        a aVar = this.D;
        if (!(this.A == null && this.f == -1) && sVar.e() == 0) {
            c(oVar);
            aVar.a();
            return;
        }
        boolean z3 = true;
        boolean z4 = (aVar.e && this.f == -1 && this.A == null) ? false : true;
        if (z4) {
            aVar.a();
            if (this.A != null) {
                a(aVar);
            } else {
                N();
                aVar.c = this.e;
            }
            a(sVar, aVar);
            aVar.e = true;
        }
        if (this.A == null && this.f == -1 && !(aVar.c == this.o && j() == this.z)) {
            this.h.a();
            aVar.d = true;
        }
        if (v() > 0 && ((savedState = this.A) == null || savedState.c < 1)) {
            if (aVar.d) {
                for (int i2 = 0; i2 < this.i; i2++) {
                    this.a[i2].e();
                    if (aVar.b != Integer.MIN_VALUE) {
                        this.a[i2].c(aVar.b);
                    }
                }
            } else if (z4 || this.D.f == null) {
                for (int i3 = 0; i3 < this.i; i3++) {
                    this.a[i3].a(this.e, aVar.b);
                }
                this.D.a(this.a);
            } else {
                for (int i4 = 0; i4 < this.i; i4++) {
                    b bVar = this.a[i4];
                    bVar.e();
                    bVar.c(this.D.f[i4]);
                }
            }
        }
        a(oVar);
        this.l.a = false;
        this.E = false;
        f(this.c.f());
        b(aVar.a, sVar);
        if (aVar.c) {
            m(-1);
            a(oVar, this.l, sVar);
            m(1);
            this.l.c = aVar.a + this.l.d;
            a(oVar, this.l, sVar);
        } else {
            m(1);
            a(oVar, this.l, sVar);
            m(-1);
            this.l.c = aVar.a + this.l.d;
            a(oVar, this.l, sVar);
        }
        O();
        if (v() > 0) {
            if (this.e) {
                b(oVar, sVar, true);
                c(oVar, sVar, false);
            } else {
                c(oVar, sVar, true);
                b(oVar, sVar, false);
            }
        }
        if (z2 && !sVar.a()) {
            if (this.n != 0 && v() > 0 && (this.E || h() != null)) {
                a(this.H);
            }
        }
        z3 = false;
        if (sVar.a()) {
            this.D.a();
        }
        this.o = aVar.c;
        this.z = j();
        if (z3) {
            this.D.a();
            a(oVar, sVar, false);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void a(RecyclerView.s sVar) {
        super.a(sVar);
        this.f = -1;
        this.g = androidx.customview.a.a.INVALID_ID;
        this.A = null;
        this.D.a();
    }

    private void O() {
        if (this.c.h() != 1073741824) {
            int v = v();
            float f2 = 0.0f;
            for (int i2 = 0; i2 < v; i2++) {
                View i3 = i(i2);
                float e2 = (float) this.c.e(i3);
                if (e2 >= f2) {
                    if (((LayoutParams) i3.getLayoutParams()).a()) {
                        e2 = (e2 * 1.0f) / ((float) this.i);
                    }
                    f2 = Math.max(f2, e2);
                }
            }
            int i4 = this.k;
            int round = Math.round(f2 * ((float) this.i));
            if (this.c.h() == Integer.MIN_VALUE) {
                round = Math.min(round, this.c.f());
            }
            f(round);
            if (this.k != i4) {
                for (int i5 = 0; i5 < v; i5++) {
                    View i6 = i(i5);
                    LayoutParams layoutParams = (LayoutParams) i6.getLayoutParams();
                    if (!layoutParams.b) {
                        if (!j() || this.j != 1) {
                            int i7 = layoutParams.a.e * this.k;
                            int i8 = layoutParams.a.e * i4;
                            if (this.j == 1) {
                                i6.offsetLeftAndRight(i7 - i8);
                            } else {
                                i6.offsetTopAndBottom(i7 - i8);
                            }
                        } else {
                            i6.offsetLeftAndRight(((-((this.i - 1) - layoutParams.a.e)) * this.k) - ((-((this.i - 1) - layoutParams.a.e)) * i4));
                        }
                    }
                }
            }
        }
    }

    private void a(a aVar) {
        if (this.A.c > 0) {
            if (this.A.c == this.i) {
                for (int i2 = 0; i2 < this.i; i2++) {
                    this.a[i2].e();
                    int i3 = this.A.d[i2];
                    if (i3 != Integer.MIN_VALUE) {
                        if (this.A.i) {
                            i3 += this.b.d();
                        } else {
                            i3 += this.b.c();
                        }
                    }
                    this.a[i2].c(i3);
                }
            } else {
                this.A.a();
                SavedState savedState = this.A;
                savedState.a = savedState.b;
            }
        }
        this.z = this.A.j;
        a(this.A.h);
        N();
        if (this.A.a != -1) {
            this.f = this.A.a;
            aVar.c = this.A.i;
        } else {
            aVar.c = this.e;
        }
        if (this.A.e > 1) {
            this.h.a = this.A.f;
            this.h.b = this.A.g;
        }
    }

    /* access modifiers changed from: package-private */
    public void a(RecyclerView.s sVar, a aVar) {
        if (!b(sVar, aVar) && !c(sVar, aVar)) {
            aVar.b();
            aVar.a = 0;
        }
    }

    private boolean c(RecyclerView.s sVar, a aVar) {
        int i2;
        if (this.o) {
            i2 = w(sVar.e());
        } else {
            i2 = v(sVar.e());
        }
        aVar.a = i2;
        aVar.b = androidx.customview.a.a.INVALID_ID;
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean b(RecyclerView.s sVar, a aVar) {
        int i2;
        int i3;
        int i4;
        boolean z2 = false;
        if (sVar.a() || (i2 = this.f) == -1) {
            return false;
        }
        if (i2 < 0 || i2 >= sVar.e()) {
            this.f = -1;
            this.g = androidx.customview.a.a.INVALID_ID;
            return false;
        }
        SavedState savedState = this.A;
        if (savedState == null || savedState.a == -1 || this.A.c < 1) {
            View c2 = c(this.f);
            if (c2 != null) {
                if (this.e) {
                    i3 = K();
                } else {
                    i3 = L();
                }
                aVar.a = i3;
                if (this.g != Integer.MIN_VALUE) {
                    if (aVar.c) {
                        aVar.b = (this.b.d() - this.g) - this.b.b(c2);
                    } else {
                        aVar.b = (this.b.c() + this.g) - this.b.a(c2);
                    }
                    return true;
                } else if (this.b.e(c2) > this.b.f()) {
                    if (aVar.c) {
                        i4 = this.b.d();
                    } else {
                        i4 = this.b.c();
                    }
                    aVar.b = i4;
                    return true;
                } else {
                    int a2 = this.b.a(c2) - this.b.c();
                    if (a2 < 0) {
                        aVar.b = -a2;
                        return true;
                    }
                    int d2 = this.b.d() - this.b.b(c2);
                    if (d2 < 0) {
                        aVar.b = d2;
                        return true;
                    }
                    aVar.b = androidx.customview.a.a.INVALID_ID;
                }
            } else {
                aVar.a = this.f;
                int i5 = this.g;
                if (i5 == Integer.MIN_VALUE) {
                    if (u(aVar.a) == 1) {
                        z2 = true;
                    }
                    aVar.c = z2;
                    aVar.b();
                } else {
                    aVar.a(i5);
                }
                aVar.d = true;
            }
        } else {
            aVar.b = androidx.customview.a.a.INVALID_ID;
            aVar.a = this.f;
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public void f(int i2) {
        this.k = i2 / this.i;
        this.B = View.MeasureSpec.makeMeasureSpec(i2, this.c.h());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public boolean b() {
        return this.A == null;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public int c(RecyclerView.s sVar) {
        return b(sVar);
    }

    private int b(RecyclerView.s sVar) {
        if (v() == 0) {
            return 0;
        }
        return k.a(sVar, this.b, b(!this.F), c(!this.F), this, this.F, this.e);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public int d(RecyclerView.s sVar) {
        return b(sVar);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public int e(RecyclerView.s sVar) {
        return i(sVar);
    }

    private int i(RecyclerView.s sVar) {
        if (v() == 0) {
            return 0;
        }
        return k.a(sVar, this.b, b(!this.F), c(!this.F), this, this.F);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public int f(RecyclerView.s sVar) {
        return i(sVar);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public int g(RecyclerView.s sVar) {
        return j(sVar);
    }

    private int j(RecyclerView.s sVar) {
        if (v() == 0) {
            return 0;
        }
        return k.b(sVar, this.b, b(!this.F), c(!this.F), this, this.F);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public int h(RecyclerView.s sVar) {
        return j(sVar);
    }

    private void a(View view, LayoutParams layoutParams, boolean z2) {
        if (layoutParams.b) {
            if (this.j == 1) {
                a(view, this.B, a(z(), x(), B() + D(), layoutParams.height, true), z2);
            } else {
                a(view, a(y(), w(), A() + C(), layoutParams.width, true), this.B, z2);
            }
        } else if (this.j == 1) {
            a(view, a(this.k, w(), 0, layoutParams.width, false), a(z(), x(), B() + D(), layoutParams.height, true), z2);
        } else {
            a(view, a(y(), w(), A() + C(), layoutParams.width, true), a(this.k, x(), 0, layoutParams.height, false), z2);
        }
    }

    private void a(View view, int i2, int i3, boolean z2) {
        boolean z3;
        b(view, this.C);
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        int b2 = b(i2, layoutParams.leftMargin + this.C.left, layoutParams.rightMargin + this.C.right);
        int b3 = b(i3, layoutParams.topMargin + this.C.top, layoutParams.bottomMargin + this.C.bottom);
        if (z2) {
            z3 = a(view, b2, b3, layoutParams);
        } else {
            z3 = b(view, b2, b3, layoutParams);
        }
        if (z3) {
            view.measure(b2, b3);
        }
    }

    private int b(int i2, int i3, int i4) {
        if (i3 == 0 && i4 == 0) {
            return i2;
        }
        int mode = View.MeasureSpec.getMode(i2);
        if (mode == Integer.MIN_VALUE || mode == 1073741824) {
            return View.MeasureSpec.makeMeasureSpec(Math.max(0, (View.MeasureSpec.getSize(i2) - i3) - i4), mode);
        }
        return i2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void a(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            this.A = (SavedState) parcelable;
            o();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public Parcelable d() {
        int i2;
        int i3;
        SavedState savedState = this.A;
        if (savedState != null) {
            return new SavedState(savedState);
        }
        SavedState savedState2 = new SavedState();
        savedState2.h = this.d;
        savedState2.i = this.o;
        savedState2.j = this.z;
        LazySpanLookup lazySpanLookup = this.h;
        if (lazySpanLookup == null || lazySpanLookup.a == null) {
            savedState2.e = 0;
        } else {
            savedState2.f = this.h.a;
            savedState2.e = savedState2.f.length;
            savedState2.g = this.h.b;
        }
        if (v() > 0) {
            if (this.o) {
                i2 = K();
            } else {
                i2 = L();
            }
            savedState2.a = i2;
            savedState2.b = k();
            int i4 = this.i;
            savedState2.c = i4;
            savedState2.d = new int[i4];
            for (int i5 = 0; i5 < this.i; i5++) {
                if (this.o) {
                    i3 = this.a[i5].b(androidx.customview.a.a.INVALID_ID);
                    if (i3 != Integer.MIN_VALUE) {
                        i3 -= this.b.d();
                    }
                } else {
                    i3 = this.a[i5].a(androidx.customview.a.a.INVALID_ID);
                    if (i3 != Integer.MIN_VALUE) {
                        i3 -= this.b.c();
                    }
                }
                savedState2.d[i5] = i3;
            }
        } else {
            savedState2.a = -1;
            savedState2.b = -1;
            savedState2.c = 0;
        }
        return savedState2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void a(RecyclerView.o oVar, RecyclerView.s sVar, View view, c cVar) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (!(layoutParams instanceof LayoutParams)) {
            super.a(view, cVar);
            return;
        }
        LayoutParams layoutParams2 = (LayoutParams) layoutParams;
        if (this.j == 0) {
            cVar.b(c.C٠٠٣٢c.a(layoutParams2.b(), layoutParams2.b ? this.i : 1, -1, -1, layoutParams2.b, false));
        } else {
            cVar.b(c.C٠٠٣٢c.a(-1, -1, layoutParams2.b(), layoutParams2.b ? this.i : 1, layoutParams2.b, false));
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void a(AccessibilityEvent accessibilityEvent) {
        super.a(accessibilityEvent);
        if (v() > 0) {
            View b2 = b(false);
            View c2 = c(false);
            if (b2 != null && c2 != null) {
                int d2 = d(b2);
                int d3 = d(c2);
                if (d2 < d3) {
                    accessibilityEvent.setFromIndex(d2);
                    accessibilityEvent.setToIndex(d3);
                    return;
                }
                accessibilityEvent.setFromIndex(d3);
                accessibilityEvent.setToIndex(d2);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public int k() {
        View view;
        if (this.e) {
            view = c(true);
        } else {
            view = b(true);
        }
        if (view == null) {
            return -1;
        }
        return d(view);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public int a(RecyclerView.o oVar, RecyclerView.s sVar) {
        if (this.j == 0) {
            return this.i;
        }
        return super.a(oVar, sVar);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public int b(RecyclerView.o oVar, RecyclerView.s sVar) {
        if (this.j == 1) {
            return this.i;
        }
        return super.b(oVar, sVar);
    }

    /* access modifiers changed from: package-private */
    public View b(boolean z2) {
        int c2 = this.b.c();
        int d2 = this.b.d();
        int v = v();
        View view = null;
        for (int i2 = 0; i2 < v; i2++) {
            View i3 = i(i2);
            int a2 = this.b.a(i3);
            if (this.b.b(i3) > c2 && a2 < d2) {
                if (a2 >= c2 || !z2) {
                    return i3;
                }
                if (view == null) {
                    view = i3;
                }
            }
        }
        return view;
    }

    /* access modifiers changed from: package-private */
    public View c(boolean z2) {
        int c2 = this.b.c();
        int d2 = this.b.d();
        View view = null;
        for (int v = v() - 1; v >= 0; v--) {
            View i2 = i(v);
            int a2 = this.b.a(i2);
            int b2 = this.b.b(i2);
            if (b2 > c2 && a2 < d2) {
                if (b2 <= d2 || !z2) {
                    return i2;
                }
                if (view == null) {
                    view = i2;
                }
            }
        }
        return view;
    }

    private void b(RecyclerView.o oVar, RecyclerView.s sVar, boolean z2) {
        int d2;
        int r = r(androidx.customview.a.a.INVALID_ID);
        if (r != Integer.MIN_VALUE && (d2 = this.b.d() - r) > 0) {
            int i2 = d2 - (-c(-d2, oVar, sVar));
            if (z2 && i2 > 0) {
                this.b.a(i2);
            }
        }
    }

    private void c(RecyclerView.o oVar, RecyclerView.s sVar, boolean z2) {
        int c2;
        int q = q(Api.BaseClientBuilder.API_PRIORITY_OTHER);
        if (q != Integer.MAX_VALUE && (c2 = q - this.b.c()) > 0) {
            int c3 = c2 - c(c2, oVar, sVar);
            if (z2 && c3 > 0) {
                this.b.a(-c3);
            }
        }
    }

    private void b(int i2, RecyclerView.s sVar) {
        int i3;
        int i4;
        int c2;
        f fVar = this.l;
        boolean z2 = false;
        fVar.b = 0;
        fVar.c = i2;
        if (!s() || (c2 = sVar.c()) == -1) {
            i4 = 0;
            i3 = 0;
        } else {
            if (this.e == (c2 < i2)) {
                i4 = this.b.f();
                i3 = 0;
            } else {
                i3 = this.b.f();
                i4 = 0;
            }
        }
        if (r()) {
            this.l.f = this.b.c() - i3;
            this.l.g = this.b.d() + i4;
        } else {
            this.l.g = this.b.e() + i4;
            this.l.f = -i3;
        }
        f fVar2 = this.l;
        fVar2.h = false;
        fVar2.a = true;
        if (this.b.h() == 0 && this.b.e() == 0) {
            z2 = true;
        }
        fVar2.i = z2;
    }

    private void m(int i2) {
        f fVar = this.l;
        fVar.e = i2;
        int i3 = 1;
        if (this.e != (i2 == -1)) {
            i3 = -1;
        }
        fVar.d = i3;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void j(int i2) {
        super.j(i2);
        for (int i3 = 0; i3 < this.i; i3++) {
            this.a[i3].d(i2);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void k(int i2) {
        super.k(i2);
        for (int i3 = 0; i3 < this.i; i3++) {
            this.a[i3].d(i2);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void b(RecyclerView recyclerView, int i2, int i3) {
        c(i2, i3, 2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void a(RecyclerView recyclerView, int i2, int i3) {
        c(i2, i3, 1);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void a(RecyclerView recyclerView) {
        this.h.a();
        o();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void a(RecyclerView recyclerView, int i2, int i3, int i4) {
        c(i2, i3, 8);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void a(RecyclerView recyclerView, int i2, int i3, Object obj) {
        c(i2, i3, 4);
    }

    private void c(int i2, int i3, int i4) {
        int i5;
        int i6;
        int K = this.e ? K() : L();
        if (i4 != 8) {
            i5 = i2 + i3;
            i6 = i2;
        } else if (i2 < i3) {
            i5 = i3 + 1;
            i6 = i2;
        } else {
            i5 = i2 + 1;
            i6 = i3;
        }
        this.h.b(i6);
        if (i4 != 8) {
            switch (i4) {
                case 1:
                    this.h.b(i2, i3);
                    break;
                case 2:
                    this.h.a(i2, i3);
                    break;
            }
        } else {
            this.h.a(i2, 1);
            this.h.b(i3, 1);
        }
        if (i5 > K) {
            if (i6 <= (this.e ? L() : K())) {
                o();
            }
        }
    }

    private int a(RecyclerView.o oVar, f fVar, RecyclerView.s sVar) {
        int i2;
        int i3;
        int i4;
        int i5;
        b bVar;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        boolean z2;
        int i12;
        int i13;
        b bVar2;
        boolean z3 = false;
        this.m.set(0, this.i, true);
        if (this.l.i) {
            i2 = fVar.e == 1 ? Api.BaseClientBuilder.API_PRIORITY_OTHER : androidx.customview.a.a.INVALID_ID;
        } else if (fVar.e == 1) {
            i2 = fVar.g + fVar.b;
        } else {
            i2 = fVar.f - fVar.b;
        }
        a(fVar.e, i2);
        if (this.e) {
            i3 = this.b.d();
        } else {
            i3 = this.b.c();
        }
        boolean z4 = false;
        while (true) {
            if (fVar.a(sVar)) {
                if (!this.l.i && this.m.isEmpty()) {
                    i4 = 0;
                    break;
                }
                View a2 = fVar.a(oVar);
                LayoutParams layoutParams = (LayoutParams) a2.getLayoutParams();
                int f2 = layoutParams.f();
                int c2 = this.h.c(f2);
                boolean z5 = c2 == -1;
                if (z5) {
                    if (layoutParams.b) {
                        b[] bVarArr = this.a;
                        char c3 = z3 ? 1 : 0;
                        char c4 = z3 ? 1 : 0;
                        char c5 = z3 ? 1 : 0;
                        bVar2 = bVarArr[c3];
                    } else {
                        bVar2 = a(fVar);
                    }
                    this.h.a(f2, bVar2);
                    bVar = bVar2;
                } else {
                    bVar = this.a[c2];
                }
                layoutParams.a = bVar;
                if (fVar.e == 1) {
                    b(a2);
                } else {
                    int i14 = z3 ? 1 : 0;
                    int i15 = z3 ? 1 : 0;
                    int i16 = z3 ? 1 : 0;
                    b(a2, i14);
                }
                a(a2, layoutParams, z3);
                if (fVar.e == 1) {
                    if (layoutParams.b) {
                        i13 = r(i3);
                    } else {
                        i13 = bVar.b(i3);
                    }
                    int e2 = this.b.e(a2) + i13;
                    if (z5 && layoutParams.b) {
                        LazySpanLookup.FullSpanItem n2 = n(i13);
                        n2.b = -1;
                        n2.a = f2;
                        this.h.a(n2);
                    }
                    i6 = e2;
                    i7 = i13;
                } else {
                    if (layoutParams.b) {
                        i12 = q(i3);
                    } else {
                        i12 = bVar.a(i3);
                    }
                    i7 = i12 - this.b.e(a2);
                    if (z5 && layoutParams.b) {
                        LazySpanLookup.FullSpanItem o2 = o(i12);
                        o2.b = 1;
                        o2.a = f2;
                        this.h.a(o2);
                    }
                    i6 = i12;
                }
                if (layoutParams.b && fVar.d == -1) {
                    if (z5) {
                        this.E = true;
                    } else {
                        if (fVar.e == 1) {
                            z2 = !m();
                        } else {
                            z2 = !n();
                        }
                        if (z2) {
                            LazySpanLookup.FullSpanItem f3 = this.h.f(f2);
                            if (f3 != null) {
                                f3.d = true;
                            }
                            this.E = true;
                        }
                    }
                }
                a(a2, layoutParams, fVar);
                if (!j() || this.j != 1) {
                    if (layoutParams.b) {
                        i10 = this.c.c();
                    } else {
                        i10 = (bVar.e * this.k) + this.c.c();
                    }
                    i9 = i10;
                    i8 = this.c.e(a2) + i10;
                } else {
                    if (layoutParams.b) {
                        i11 = this.c.d();
                    } else {
                        i11 = this.c.d() - (((this.i - 1) - bVar.e) * this.k);
                    }
                    i8 = i11;
                    i9 = i11 - this.c.e(a2);
                }
                if (this.j == 1) {
                    a(a2, i9, i7, i8, i6);
                } else {
                    a(a2, i7, i9, i6, i8);
                }
                if (layoutParams.b) {
                    a(this.l.e, i2);
                } else {
                    a(bVar, this.l.e, i2);
                }
                a(oVar, this.l);
                if (this.l.h && a2.hasFocusable()) {
                    if (layoutParams.b) {
                        this.m.clear();
                    } else {
                        this.m.set(bVar.e, false);
                    }
                }
                z4 = true;
                z3 = false;
            } else {
                i4 = 0;
                break;
            }
        }
        if (!z4) {
            a(oVar, this.l);
        }
        if (this.l.e == -1) {
            i5 = this.b.c() - q(this.b.c());
        } else {
            i5 = r(this.b.d()) - this.b.d();
        }
        return i5 > 0 ? Math.min(fVar.b, i5) : i4;
    }

    private LazySpanLookup.FullSpanItem n(int i2) {
        LazySpanLookup.FullSpanItem fullSpanItem = new LazySpanLookup.FullSpanItem();
        fullSpanItem.c = new int[this.i];
        for (int i3 = 0; i3 < this.i; i3++) {
            fullSpanItem.c[i3] = i2 - this.a[i3].b(i2);
        }
        return fullSpanItem;
    }

    private LazySpanLookup.FullSpanItem o(int i2) {
        LazySpanLookup.FullSpanItem fullSpanItem = new LazySpanLookup.FullSpanItem();
        fullSpanItem.c = new int[this.i];
        for (int i3 = 0; i3 < this.i; i3++) {
            fullSpanItem.c[i3] = this.a[i3].a(i2) - i2;
        }
        return fullSpanItem;
    }

    private void a(View view, LayoutParams layoutParams, f fVar) {
        if (fVar.e == 1) {
            if (layoutParams.b) {
                p(view);
            } else {
                layoutParams.a.b(view);
            }
        } else if (layoutParams.b) {
            q(view);
        } else {
            layoutParams.a.a(view);
        }
    }

    private void a(RecyclerView.o oVar, f fVar) {
        int i2;
        int i3;
        if (fVar.a && !fVar.i) {
            if (fVar.b == 0) {
                if (fVar.e == -1) {
                    b(oVar, fVar.g);
                } else {
                    a(oVar, fVar.f);
                }
            } else if (fVar.e == -1) {
                int p = fVar.f - p(fVar.f);
                if (p < 0) {
                    i3 = fVar.g;
                } else {
                    i3 = fVar.g - Math.min(p, fVar.b);
                }
                b(oVar, i3);
            } else {
                int s = s(fVar.g) - fVar.g;
                if (s < 0) {
                    i2 = fVar.f;
                } else {
                    i2 = Math.min(s, fVar.b) + fVar.f;
                }
                a(oVar, i2);
            }
        }
    }

    private void p(View view) {
        for (int i2 = this.i - 1; i2 >= 0; i2--) {
            this.a[i2].b(view);
        }
    }

    private void q(View view) {
        for (int i2 = this.i - 1; i2 >= 0; i2--) {
            this.a[i2].a(view);
        }
    }

    private void a(int i2, int i3) {
        for (int i4 = 0; i4 < this.i; i4++) {
            if (!this.a[i4].a.isEmpty()) {
                a(this.a[i4], i2, i3);
            }
        }
    }

    private void a(b bVar, int i2, int i3) {
        int i4 = bVar.i();
        if (i2 == -1) {
            if (bVar.b() + i4 <= i3) {
                this.m.set(bVar.e, false);
            }
        } else if (bVar.d() - i4 >= i3) {
            this.m.set(bVar.e, false);
        }
    }

    private int p(int i2) {
        int a2 = this.a[0].a(i2);
        for (int i3 = 1; i3 < this.i; i3++) {
            int a3 = this.a[i3].a(i2);
            if (a3 > a2) {
                a2 = a3;
            }
        }
        return a2;
    }

    private int q(int i2) {
        int a2 = this.a[0].a(i2);
        for (int i3 = 1; i3 < this.i; i3++) {
            int a3 = this.a[i3].a(i2);
            if (a3 < a2) {
                a2 = a3;
            }
        }
        return a2;
    }

    /* access modifiers changed from: package-private */
    public boolean m() {
        int b2 = this.a[0].b(androidx.customview.a.a.INVALID_ID);
        for (int i2 = 1; i2 < this.i; i2++) {
            if (this.a[i2].b(androidx.customview.a.a.INVALID_ID) != b2) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean n() {
        int a2 = this.a[0].a(androidx.customview.a.a.INVALID_ID);
        for (int i2 = 1; i2 < this.i; i2++) {
            if (this.a[i2].a(androidx.customview.a.a.INVALID_ID) != a2) {
                return false;
            }
        }
        return true;
    }

    private int r(int i2) {
        int b2 = this.a[0].b(i2);
        for (int i3 = 1; i3 < this.i; i3++) {
            int b3 = this.a[i3].b(i2);
            if (b3 > b2) {
                b2 = b3;
            }
        }
        return b2;
    }

    private int s(int i2) {
        int b2 = this.a[0].b(i2);
        for (int i3 = 1; i3 < this.i; i3++) {
            int b3 = this.a[i3].b(i2);
            if (b3 < b2) {
                b2 = b3;
            }
        }
        return b2;
    }

    private void a(RecyclerView.o oVar, int i2) {
        while (v() > 0) {
            View i3 = i(0);
            if (this.b.b(i3) <= i2 && this.b.c(i3) <= i2) {
                LayoutParams layoutParams = (LayoutParams) i3.getLayoutParams();
                if (layoutParams.b) {
                    for (int i4 = 0; i4 < this.i; i4++) {
                        if (this.a[i4].a.size() == 1) {
                            return;
                        }
                    }
                    for (int i5 = 0; i5 < this.i; i5++) {
                        this.a[i5].h();
                    }
                } else if (layoutParams.a.a.size() != 1) {
                    layoutParams.a.h();
                } else {
                    return;
                }
                a(i3, oVar);
            } else {
                return;
            }
        }
    }

    private void b(RecyclerView.o oVar, int i2) {
        for (int v = v() - 1; v >= 0; v--) {
            View i3 = i(v);
            if (this.b.a(i3) >= i2 && this.b.d(i3) >= i2) {
                LayoutParams layoutParams = (LayoutParams) i3.getLayoutParams();
                if (layoutParams.b) {
                    for (int i4 = 0; i4 < this.i; i4++) {
                        if (this.a[i4].a.size() == 1) {
                            return;
                        }
                    }
                    for (int i5 = 0; i5 < this.i; i5++) {
                        this.a[i5].g();
                    }
                } else if (layoutParams.a.a.size() != 1) {
                    layoutParams.a.g();
                } else {
                    return;
                }
                a(i3, oVar);
            } else {
                return;
            }
        }
    }

    private boolean t(int i2) {
        if (this.j == 0) {
            if ((i2 == -1) != this.e) {
                return true;
            }
            return false;
        }
        if (((i2 == -1) == this.e) == j()) {
            return true;
        }
        return false;
    }

    private b a(f fVar) {
        int i2;
        int i3;
        int i4 = -1;
        if (t(fVar.e)) {
            i3 = this.i - 1;
            i2 = -1;
        } else {
            i3 = 0;
            i4 = this.i;
            i2 = 1;
        }
        b bVar = null;
        if (fVar.e == 1) {
            int i5 = Api.BaseClientBuilder.API_PRIORITY_OTHER;
            int c2 = this.b.c();
            while (i3 != i4) {
                b bVar2 = this.a[i3];
                int b2 = bVar2.b(c2);
                if (b2 < i5) {
                    bVar = bVar2;
                    i5 = b2;
                }
                i3 += i2;
            }
            return bVar;
        }
        int i6 = androidx.customview.a.a.INVALID_ID;
        int d2 = this.b.d();
        while (i3 != i4) {
            b bVar3 = this.a[i3];
            int a2 = bVar3.a(d2);
            if (a2 > i6) {
                bVar = bVar3;
                i6 = a2;
            }
            i3 += i2;
        }
        return bVar;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public boolean f() {
        return this.j == 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public boolean e() {
        return this.j == 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public int a(int i2, RecyclerView.o oVar, RecyclerView.s sVar) {
        return c(i2, oVar, sVar);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public int b(int i2, RecyclerView.o oVar, RecyclerView.s sVar) {
        return c(i2, oVar, sVar);
    }

    private int u(int i2) {
        if (v() != 0) {
            if ((i2 < L()) != this.e) {
                return -1;
            }
            return 1;
        } else if (this.e) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.r.b
    public PointF d(int i2) {
        int u = u(i2);
        PointF pointF = new PointF();
        if (u == 0) {
            return null;
        }
        if (this.j == 0) {
            pointF.x = (float) u;
            pointF.y = 0.0f;
        } else {
            pointF.x = 0.0f;
            pointF.y = (float) u;
        }
        return pointF;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void a(RecyclerView recyclerView, RecyclerView.s sVar, int i2) {
        g gVar = new g(recyclerView.getContext());
        gVar.c(i2);
        a(gVar);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void e(int i2) {
        SavedState savedState = this.A;
        if (!(savedState == null || savedState.a == i2)) {
            this.A.b();
        }
        this.f = i2;
        this.g = androidx.customview.a.a.INVALID_ID;
        o();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void a(int i2, int i3, RecyclerView.s sVar, RecyclerView.i.a aVar) {
        int i4;
        if (this.j != 0) {
            i2 = i3;
        }
        if (!(v() == 0 || i2 == 0)) {
            a(i2, sVar);
            int[] iArr = this.G;
            if (iArr == null || iArr.length < this.i) {
                this.G = new int[this.i];
            }
            int i5 = 0;
            for (int i6 = 0; i6 < this.i; i6++) {
                if (this.l.d == -1) {
                    i4 = this.l.f - this.a[i6].a(this.l.f);
                } else {
                    i4 = this.a[i6].b(this.l.g) - this.l.g;
                }
                if (i4 >= 0) {
                    this.G[i5] = i4;
                    i5++;
                }
            }
            Arrays.sort(this.G, 0, i5);
            for (int i7 = 0; i7 < i5 && this.l.a(sVar); i7++) {
                aVar.b(this.l.c, this.G[i7]);
                this.l.c += this.l.d;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void a(int i2, RecyclerView.s sVar) {
        int i3;
        int i4;
        if (i2 > 0) {
            i4 = K();
            i3 = 1;
        } else {
            i4 = L();
            i3 = -1;
        }
        this.l.a = true;
        b(i4, sVar);
        m(i3);
        f fVar = this.l;
        fVar.c = i4 + fVar.d;
        this.l.b = Math.abs(i2);
    }

    /* access modifiers changed from: package-private */
    public int c(int i2, RecyclerView.o oVar, RecyclerView.s sVar) {
        if (v() == 0 || i2 == 0) {
            return 0;
        }
        a(i2, sVar);
        int a2 = a(oVar, this.l, sVar);
        if (this.l.b >= a2) {
            i2 = i2 < 0 ? -a2 : a2;
        }
        this.b.a(-i2);
        this.o = this.e;
        f fVar = this.l;
        fVar.b = 0;
        a(oVar, fVar);
        return i2;
    }

    /* access modifiers changed from: package-private */
    public int K() {
        int v = v();
        if (v == 0) {
            return 0;
        }
        return d(i(v - 1));
    }

    /* access modifiers changed from: package-private */
    public int L() {
        if (v() == 0) {
            return 0;
        }
        return d(i(0));
    }

    private int v(int i2) {
        int v = v();
        for (int i3 = 0; i3 < v; i3++) {
            int d2 = d(i(i3));
            if (d2 >= 0 && d2 < i2) {
                return d2;
            }
        }
        return 0;
    }

    private int w(int i2) {
        for (int v = v() - 1; v >= 0; v--) {
            int d2 = d(i(v));
            if (d2 >= 0 && d2 < i2) {
                return d2;
            }
        }
        return 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public RecyclerView.LayoutParams a() {
        if (this.j == 0) {
            return new LayoutParams(-2, -1);
        }
        return new LayoutParams(-1, -2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public RecyclerView.LayoutParams a(Context context, AttributeSet attributeSet) {
        return new LayoutParams(context, attributeSet);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public RecyclerView.LayoutParams a(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public boolean a(RecyclerView.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public View a(View view, int i2, RecyclerView.o oVar, RecyclerView.s sVar) {
        View e2;
        int i3;
        int i4;
        int i5;
        int i6;
        View a2;
        if (v() == 0 || (e2 = e(view)) == null) {
            return null;
        }
        N();
        int x = x(i2);
        if (x == Integer.MIN_VALUE) {
            return null;
        }
        LayoutParams layoutParams = (LayoutParams) e2.getLayoutParams();
        boolean z2 = layoutParams.b;
        b bVar = layoutParams.a;
        if (x == 1) {
            i3 = K();
        } else {
            i3 = L();
        }
        b(i3, sVar);
        m(x);
        f fVar = this.l;
        fVar.c = fVar.d + i3;
        this.l.b = (int) (((float) this.b.f()) * 0.33333334f);
        f fVar2 = this.l;
        fVar2.h = true;
        fVar2.a = false;
        a(oVar, fVar2, sVar);
        this.o = this.e;
        if (!(z2 || (a2 = bVar.a(i3, x)) == null || a2 == e2)) {
            return a2;
        }
        if (t(x)) {
            for (int i7 = this.i - 1; i7 >= 0; i7--) {
                View a3 = this.a[i7].a(i3, x);
                if (!(a3 == null || a3 == e2)) {
                    return a3;
                }
            }
        } else {
            for (int i8 = 0; i8 < this.i; i8++) {
                View a4 = this.a[i8].a(i3, x);
                if (!(a4 == null || a4 == e2)) {
                    return a4;
                }
            }
        }
        boolean z3 = (this.d ^ true) == (x == -1);
        if (!z2) {
            if (z3) {
                i6 = bVar.j();
            } else {
                i6 = bVar.k();
            }
            View c2 = c(i6);
            if (!(c2 == null || c2 == e2)) {
                return c2;
            }
        }
        if (t(x)) {
            for (int i9 = this.i - 1; i9 >= 0; i9--) {
                if (i9 != bVar.e) {
                    if (z3) {
                        i5 = this.a[i9].j();
                    } else {
                        i5 = this.a[i9].k();
                    }
                    View c3 = c(i5);
                    if (!(c3 == null || c3 == e2)) {
                        return c3;
                    }
                }
            }
        } else {
            for (int i10 = 0; i10 < this.i; i10++) {
                if (z3) {
                    i4 = this.a[i10].j();
                } else {
                    i4 = this.a[i10].k();
                }
                View c4 = c(i4);
                if (!(c4 == null || c4 == e2)) {
                    return c4;
                }
            }
        }
        return null;
    }

    private int x(int i2) {
        if (i2 != 17) {
            if (i2 != 33) {
                if (i2 != 66) {
                    if (i2 != 130) {
                        switch (i2) {
                            case 1:
                                return (this.j != 1 && j()) ? 1 : -1;
                            case 2:
                                return (this.j != 1 && j()) ? -1 : 1;
                            default:
                                return androidx.customview.a.a.INVALID_ID;
                        }
                    } else if (this.j == 1) {
                        return 1;
                    } else {
                        return androidx.customview.a.a.INVALID_ID;
                    }
                } else if (this.j == 0) {
                    return 1;
                } else {
                    return androidx.customview.a.a.INVALID_ID;
                }
            } else if (this.j == 1) {
                return -1;
            } else {
                return androidx.customview.a.a.INVALID_ID;
            }
        } else if (this.j == 0) {
            return -1;
        } else {
            return androidx.customview.a.a.INVALID_ID;
        }
    }

    public static class LayoutParams extends RecyclerView.LayoutParams {
        b a;
        boolean b;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public boolean a() {
            return this.b;
        }

        public final int b() {
            b bVar = this.a;
            if (bVar == null) {
                return -1;
            }
            return bVar.e;
        }
    }

    /* access modifiers changed from: package-private */
    public class b {
        ArrayList<View> a = new ArrayList<>();
        int b = androidx.customview.a.a.INVALID_ID;
        int c = androidx.customview.a.a.INVALID_ID;
        int d = 0;
        final int e;

        b(int i) {
            this.e = i;
        }

        /* access modifiers changed from: package-private */
        public int a(int i) {
            int i2 = this.b;
            if (i2 != Integer.MIN_VALUE) {
                return i2;
            }
            if (this.a.size() == 0) {
                return i;
            }
            a();
            return this.b;
        }

        /* access modifiers changed from: package-private */
        public void a() {
            LazySpanLookup.FullSpanItem f2;
            View view = this.a.get(0);
            LayoutParams c2 = c(view);
            this.b = StaggeredGridLayoutManager.this.b.a(view);
            if (c2.b && (f2 = StaggeredGridLayoutManager.this.h.f(c2.f())) != null && f2.b == -1) {
                this.b -= f2.a(this.e);
            }
        }

        /* access modifiers changed from: package-private */
        public int b() {
            int i = this.b;
            if (i != Integer.MIN_VALUE) {
                return i;
            }
            a();
            return this.b;
        }

        /* access modifiers changed from: package-private */
        public int b(int i) {
            int i2 = this.c;
            if (i2 != Integer.MIN_VALUE) {
                return i2;
            }
            if (this.a.size() == 0) {
                return i;
            }
            c();
            return this.c;
        }

        /* access modifiers changed from: package-private */
        public void c() {
            LazySpanLookup.FullSpanItem f2;
            ArrayList<View> arrayList = this.a;
            View view = arrayList.get(arrayList.size() - 1);
            LayoutParams c2 = c(view);
            this.c = StaggeredGridLayoutManager.this.b.b(view);
            if (c2.b && (f2 = StaggeredGridLayoutManager.this.h.f(c2.f())) != null && f2.b == 1) {
                this.c += f2.a(this.e);
            }
        }

        /* access modifiers changed from: package-private */
        public int d() {
            int i = this.c;
            if (i != Integer.MIN_VALUE) {
                return i;
            }
            c();
            return this.c;
        }

        /* access modifiers changed from: package-private */
        public void a(View view) {
            LayoutParams c2 = c(view);
            c2.a = this;
            this.a.add(0, view);
            this.b = androidx.customview.a.a.INVALID_ID;
            if (this.a.size() == 1) {
                this.c = androidx.customview.a.a.INVALID_ID;
            }
            if (c2.d() || c2.e()) {
                this.d += StaggeredGridLayoutManager.this.b.e(view);
            }
        }

        /* access modifiers changed from: package-private */
        public void b(View view) {
            LayoutParams c2 = c(view);
            c2.a = this;
            this.a.add(view);
            this.c = androidx.customview.a.a.INVALID_ID;
            if (this.a.size() == 1) {
                this.b = androidx.customview.a.a.INVALID_ID;
            }
            if (c2.d() || c2.e()) {
                this.d += StaggeredGridLayoutManager.this.b.e(view);
            }
        }

        /* access modifiers changed from: package-private */
        public void a(boolean z, int i) {
            int i2;
            if (z) {
                i2 = b(androidx.customview.a.a.INVALID_ID);
            } else {
                i2 = a(androidx.customview.a.a.INVALID_ID);
            }
            e();
            if (i2 != Integer.MIN_VALUE) {
                if (z && i2 < StaggeredGridLayoutManager.this.b.d()) {
                    return;
                }
                if (z || i2 <= StaggeredGridLayoutManager.this.b.c()) {
                    if (i != Integer.MIN_VALUE) {
                        i2 += i;
                    }
                    this.c = i2;
                    this.b = i2;
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void e() {
            this.a.clear();
            f();
            this.d = 0;
        }

        /* access modifiers changed from: package-private */
        public void f() {
            this.b = androidx.customview.a.a.INVALID_ID;
            this.c = androidx.customview.a.a.INVALID_ID;
        }

        /* access modifiers changed from: package-private */
        public void c(int i) {
            this.b = i;
            this.c = i;
        }

        /* access modifiers changed from: package-private */
        public void g() {
            int size = this.a.size();
            View remove = this.a.remove(size - 1);
            LayoutParams c2 = c(remove);
            c2.a = null;
            if (c2.d() || c2.e()) {
                this.d -= StaggeredGridLayoutManager.this.b.e(remove);
            }
            if (size == 1) {
                this.b = androidx.customview.a.a.INVALID_ID;
            }
            this.c = androidx.customview.a.a.INVALID_ID;
        }

        /* access modifiers changed from: package-private */
        public void h() {
            View remove = this.a.remove(0);
            LayoutParams c2 = c(remove);
            c2.a = null;
            if (this.a.size() == 0) {
                this.c = androidx.customview.a.a.INVALID_ID;
            }
            if (c2.d() || c2.e()) {
                this.d -= StaggeredGridLayoutManager.this.b.e(remove);
            }
            this.b = androidx.customview.a.a.INVALID_ID;
        }

        public int i() {
            return this.d;
        }

        /* access modifiers changed from: package-private */
        public LayoutParams c(View view) {
            return (LayoutParams) view.getLayoutParams();
        }

        /* access modifiers changed from: package-private */
        public void d(int i) {
            int i2 = this.b;
            if (i2 != Integer.MIN_VALUE) {
                this.b = i2 + i;
            }
            int i3 = this.c;
            if (i3 != Integer.MIN_VALUE) {
                this.c = i3 + i;
            }
        }

        public int j() {
            if (StaggeredGridLayoutManager.this.d) {
                return a(this.a.size() - 1, -1, true);
            }
            return a(0, this.a.size(), true);
        }

        public int k() {
            if (StaggeredGridLayoutManager.this.d) {
                return a(0, this.a.size(), true);
            }
            return a(this.a.size() - 1, -1, true);
        }

        /* access modifiers changed from: package-private */
        public int a(int i, int i2, boolean z, boolean z2, boolean z3) {
            int c2 = StaggeredGridLayoutManager.this.b.c();
            int d2 = StaggeredGridLayoutManager.this.b.d();
            int i3 = i2 > i ? 1 : -1;
            while (i != i2) {
                View view = this.a.get(i);
                int a2 = StaggeredGridLayoutManager.this.b.a(view);
                int b2 = StaggeredGridLayoutManager.this.b.b(view);
                boolean z4 = false;
                boolean z5 = !z3 ? a2 < d2 : a2 <= d2;
                if (!z3 ? b2 > c2 : b2 >= c2) {
                    z4 = true;
                }
                if (z5 && z4) {
                    if (!z || !z2) {
                        if (z2) {
                            return StaggeredGridLayoutManager.this.d(view);
                        }
                        if (a2 < c2 || b2 > d2) {
                            return StaggeredGridLayoutManager.this.d(view);
                        }
                    } else if (a2 >= c2 && b2 <= d2) {
                        return StaggeredGridLayoutManager.this.d(view);
                    }
                }
                i += i3;
            }
            return -1;
        }

        /* access modifiers changed from: package-private */
        public int a(int i, int i2, boolean z) {
            return a(i, i2, false, false, z);
        }

        public View a(int i, int i2) {
            View view = null;
            if (i2 != -1) {
                int size = this.a.size() - 1;
                while (size >= 0) {
                    View view2 = this.a.get(size);
                    if ((StaggeredGridLayoutManager.this.d && StaggeredGridLayoutManager.this.d(view2) >= i) || ((!StaggeredGridLayoutManager.this.d && StaggeredGridLayoutManager.this.d(view2) <= i) || !view2.hasFocusable())) {
                        break;
                    }
                    size--;
                    view = view2;
                }
            } else {
                int size2 = this.a.size();
                int i3 = 0;
                while (i3 < size2) {
                    View view3 = this.a.get(i3);
                    if ((StaggeredGridLayoutManager.this.d && StaggeredGridLayoutManager.this.d(view3) <= i) || ((!StaggeredGridLayoutManager.this.d && StaggeredGridLayoutManager.this.d(view3) >= i) || !view3.hasFocusable())) {
                        break;
                    }
                    i3++;
                    view = view3;
                }
            }
            return view;
        }
    }

    /* access modifiers changed from: package-private */
    public static class LazySpanLookup {
        int[] a;
        List<FullSpanItem> b;

        LazySpanLookup() {
        }

        /* access modifiers changed from: package-private */
        public int a(int i) {
            List<FullSpanItem> list = this.b;
            if (list != null) {
                for (int size = list.size() - 1; size >= 0; size--) {
                    if (this.b.get(size).a >= i) {
                        this.b.remove(size);
                    }
                }
            }
            return b(i);
        }

        /* access modifiers changed from: package-private */
        public int b(int i) {
            int[] iArr = this.a;
            if (iArr == null || i >= iArr.length) {
                return -1;
            }
            int g = g(i);
            if (g == -1) {
                int[] iArr2 = this.a;
                Arrays.fill(iArr2, i, iArr2.length, -1);
                return this.a.length;
            }
            int i2 = g + 1;
            Arrays.fill(this.a, i, i2, -1);
            return i2;
        }

        /* access modifiers changed from: package-private */
        public int c(int i) {
            int[] iArr = this.a;
            if (iArr == null || i >= iArr.length) {
                return -1;
            }
            return iArr[i];
        }

        /* access modifiers changed from: package-private */
        public void a(int i, b bVar) {
            e(i);
            this.a[i] = bVar.e;
        }

        /* access modifiers changed from: package-private */
        public int d(int i) {
            int length = this.a.length;
            while (length <= i) {
                length *= 2;
            }
            return length;
        }

        /* access modifiers changed from: package-private */
        public void e(int i) {
            int[] iArr = this.a;
            if (iArr == null) {
                this.a = new int[(Math.max(i, 10) + 1)];
                Arrays.fill(this.a, -1);
            } else if (i >= iArr.length) {
                this.a = new int[d(i)];
                System.arraycopy(iArr, 0, this.a, 0, iArr.length);
                int[] iArr2 = this.a;
                Arrays.fill(iArr2, iArr.length, iArr2.length, -1);
            }
        }

        /* access modifiers changed from: package-private */
        public void a() {
            int[] iArr = this.a;
            if (iArr != null) {
                Arrays.fill(iArr, -1);
            }
            this.b = null;
        }

        /* access modifiers changed from: package-private */
        public void a(int i, int i2) {
            int[] iArr = this.a;
            if (iArr != null && i < iArr.length) {
                int i3 = i + i2;
                e(i3);
                int[] iArr2 = this.a;
                System.arraycopy(iArr2, i3, iArr2, i, (iArr2.length - i) - i2);
                int[] iArr3 = this.a;
                Arrays.fill(iArr3, iArr3.length - i2, iArr3.length, -1);
                c(i, i2);
            }
        }

        private void c(int i, int i2) {
            List<FullSpanItem> list = this.b;
            if (list != null) {
                int i3 = i + i2;
                for (int size = list.size() - 1; size >= 0; size--) {
                    FullSpanItem fullSpanItem = this.b.get(size);
                    if (fullSpanItem.a >= i) {
                        if (fullSpanItem.a < i3) {
                            this.b.remove(size);
                        } else {
                            fullSpanItem.a -= i2;
                        }
                    }
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void b(int i, int i2) {
            int[] iArr = this.a;
            if (iArr != null && i < iArr.length) {
                int i3 = i + i2;
                e(i3);
                int[] iArr2 = this.a;
                System.arraycopy(iArr2, i, iArr2, i3, (iArr2.length - i) - i2);
                Arrays.fill(this.a, i, i3, -1);
                d(i, i2);
            }
        }

        private void d(int i, int i2) {
            List<FullSpanItem> list = this.b;
            if (list != null) {
                for (int size = list.size() - 1; size >= 0; size--) {
                    FullSpanItem fullSpanItem = this.b.get(size);
                    if (fullSpanItem.a >= i) {
                        fullSpanItem.a += i2;
                    }
                }
            }
        }

        private int g(int i) {
            if (this.b == null) {
                return -1;
            }
            FullSpanItem f = f(i);
            if (f != null) {
                this.b.remove(f);
            }
            int size = this.b.size();
            int i2 = 0;
            while (true) {
                if (i2 >= size) {
                    i2 = -1;
                    break;
                } else if (this.b.get(i2).a >= i) {
                    break;
                } else {
                    i2++;
                }
            }
            if (i2 == -1) {
                return -1;
            }
            this.b.remove(i2);
            return this.b.get(i2).a;
        }

        public void a(FullSpanItem fullSpanItem) {
            if (this.b == null) {
                this.b = new ArrayList();
            }
            int size = this.b.size();
            for (int i = 0; i < size; i++) {
                FullSpanItem fullSpanItem2 = this.b.get(i);
                if (fullSpanItem2.a == fullSpanItem.a) {
                    this.b.remove(i);
                }
                if (fullSpanItem2.a >= fullSpanItem.a) {
                    this.b.add(i, fullSpanItem);
                    return;
                }
            }
            this.b.add(fullSpanItem);
        }

        public FullSpanItem f(int i) {
            List<FullSpanItem> list = this.b;
            if (list == null) {
                return null;
            }
            for (int size = list.size() - 1; size >= 0; size--) {
                FullSpanItem fullSpanItem = this.b.get(size);
                if (fullSpanItem.a == i) {
                    return fullSpanItem;
                }
            }
            return null;
        }

        public FullSpanItem a(int i, int i2, int i3, boolean z) {
            List<FullSpanItem> list = this.b;
            if (list == null) {
                return null;
            }
            int size = list.size();
            for (int i4 = 0; i4 < size; i4++) {
                FullSpanItem fullSpanItem = this.b.get(i4);
                if (fullSpanItem.a >= i2) {
                    return null;
                }
                if (fullSpanItem.a >= i && (i3 == 0 || fullSpanItem.b == i3 || (z && fullSpanItem.d))) {
                    return fullSpanItem;
                }
            }
            return null;
        }

        /* access modifiers changed from: package-private */
        public static class FullSpanItem implements Parcelable {
            public static final Parcelable.Creator<FullSpanItem> CREATOR = new Parcelable.Creator<FullSpanItem>() {
                /* class androidx.recyclerview.widget.StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem.AnonymousClass1 */

                /* renamed from: a */
                public FullSpanItem createFromParcel(Parcel parcel) {
                    return new FullSpanItem(parcel);
                }

                /* renamed from: a */
                public FullSpanItem[] newArray(int i) {
                    return new FullSpanItem[i];
                }
            };
            int a;
            int b;
            int[] c;
            boolean d;

            public int describeContents() {
                return 0;
            }

            FullSpanItem(Parcel parcel) {
                this.a = parcel.readInt();
                this.b = parcel.readInt();
                this.d = parcel.readInt() != 1 ? false : true;
                int readInt = parcel.readInt();
                if (readInt > 0) {
                    this.c = new int[readInt];
                    parcel.readIntArray(this.c);
                }
            }

            FullSpanItem() {
            }

            /* access modifiers changed from: package-private */
            public int a(int i) {
                int[] iArr = this.c;
                if (iArr == null) {
                    return 0;
                }
                return iArr[i];
            }

            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeInt(this.a);
                parcel.writeInt(this.b);
                parcel.writeInt(this.d ? 1 : 0);
                int[] iArr = this.c;
                if (iArr == null || iArr.length <= 0) {
                    parcel.writeInt(0);
                    return;
                }
                parcel.writeInt(iArr.length);
                parcel.writeIntArray(this.c);
            }

            public String toString() {
                return "FullSpanItem{mPosition=" + this.a + ", mGapDir=" + this.b + ", mHasUnwantedGapAfter=" + this.d + ", mGapPerSpan=" + Arrays.toString(this.c) + JSONTranscoder.OBJ_END;
            }
        }
    }

    public static class SavedState implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            /* class androidx.recyclerview.widget.StaggeredGridLayoutManager.SavedState.AnonymousClass1 */

            /* renamed from: a */
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            /* renamed from: a */
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        int a;
        int b;
        int c;
        int[] d;
        int e;
        int[] f;
        List<LazySpanLookup.FullSpanItem> g;
        boolean h;
        boolean i;
        boolean j;

        public int describeContents() {
            return 0;
        }

        public SavedState() {
        }

        SavedState(Parcel parcel) {
            this.a = parcel.readInt();
            this.b = parcel.readInt();
            this.c = parcel.readInt();
            int i2 = this.c;
            if (i2 > 0) {
                this.d = new int[i2];
                parcel.readIntArray(this.d);
            }
            this.e = parcel.readInt();
            int i3 = this.e;
            if (i3 > 0) {
                this.f = new int[i3];
                parcel.readIntArray(this.f);
            }
            boolean z = false;
            this.h = parcel.readInt() == 1;
            this.i = parcel.readInt() == 1;
            this.j = parcel.readInt() == 1 ? true : z;
            this.g = parcel.readArrayList(LazySpanLookup.FullSpanItem.class.getClassLoader());
        }

        public SavedState(SavedState savedState) {
            this.c = savedState.c;
            this.a = savedState.a;
            this.b = savedState.b;
            this.d = savedState.d;
            this.e = savedState.e;
            this.f = savedState.f;
            this.h = savedState.h;
            this.i = savedState.i;
            this.j = savedState.j;
            this.g = savedState.g;
        }

        /* access modifiers changed from: package-private */
        public void a() {
            this.d = null;
            this.c = 0;
            this.e = 0;
            this.f = null;
            this.g = null;
        }

        /* access modifiers changed from: package-private */
        public void b() {
            this.d = null;
            this.c = 0;
            this.a = -1;
            this.b = -1;
        }

        public void writeToParcel(Parcel parcel, int i2) {
            parcel.writeInt(this.a);
            parcel.writeInt(this.b);
            parcel.writeInt(this.c);
            if (this.c > 0) {
                parcel.writeIntArray(this.d);
            }
            parcel.writeInt(this.e);
            if (this.e > 0) {
                parcel.writeIntArray(this.f);
            }
            parcel.writeInt(this.h ? 1 : 0);
            parcel.writeInt(this.i ? 1 : 0);
            parcel.writeInt(this.j ? 1 : 0);
            parcel.writeList(this.g);
        }
    }

    /* access modifiers changed from: package-private */
    public class a {
        int a;
        int b;
        boolean c;
        boolean d;
        boolean e;
        int[] f;

        a() {
            a();
        }

        /* access modifiers changed from: package-private */
        public void a() {
            this.a = -1;
            this.b = androidx.customview.a.a.INVALID_ID;
            this.c = false;
            this.d = false;
            this.e = false;
            int[] iArr = this.f;
            if (iArr != null) {
                Arrays.fill(iArr, -1);
            }
        }

        /* access modifiers changed from: package-private */
        public void a(b[] bVarArr) {
            int length = bVarArr.length;
            int[] iArr = this.f;
            if (iArr == null || iArr.length < length) {
                this.f = new int[StaggeredGridLayoutManager.this.a.length];
            }
            for (int i = 0; i < length; i++) {
                this.f[i] = bVarArr[i].a(androidx.customview.a.a.INVALID_ID);
            }
        }

        /* access modifiers changed from: package-private */
        public void b() {
            int i;
            if (this.c) {
                i = StaggeredGridLayoutManager.this.b.d();
            } else {
                i = StaggeredGridLayoutManager.this.b.c();
            }
            this.b = i;
        }

        /* access modifiers changed from: package-private */
        public void a(int i) {
            if (this.c) {
                this.b = StaggeredGridLayoutManager.this.b.d() - i;
            } else {
                this.b = StaggeredGridLayoutManager.this.b.c() + i;
            }
        }
    }
}
