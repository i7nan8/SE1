package androidx.recyclerview.widget;

import android.content.Context;
import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.common.api.Api;
import java.util.List;
import org.azeckoski.reflectutils.transcoders.JSONTranscoder;

public class LinearLayoutManager extends RecyclerView.i implements RecyclerView.r.b {
    private c a;
    private boolean b;
    private boolean c;
    private boolean d;
    private boolean e;
    private boolean f;
    private final b g;
    private int h;
    int i;
    i j;
    boolean k;
    int l;
    int m;
    SavedState n;
    final a o;

    /* access modifiers changed from: package-private */
    public void a(RecyclerView.o oVar, RecyclerView.s sVar, a aVar, int i2) {
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public boolean c() {
        return true;
    }

    public LinearLayoutManager(Context context) {
        this(context, 1, false);
    }

    public LinearLayoutManager(Context context, int i2, boolean z) {
        this.i = 1;
        this.c = false;
        this.k = false;
        this.d = false;
        this.e = true;
        this.l = -1;
        this.m = androidx.customview.a.a.INVALID_ID;
        this.n = null;
        this.o = new a();
        this.g = new b();
        this.h = 2;
        b(i2);
        b(z);
    }

    public LinearLayoutManager(Context context, AttributeSet attributeSet, int i2, int i3) {
        this.i = 1;
        this.c = false;
        this.k = false;
        this.d = false;
        this.e = true;
        this.l = -1;
        this.m = androidx.customview.a.a.INVALID_ID;
        this.n = null;
        this.o = new a();
        this.g = new b();
        this.h = 2;
        RecyclerView.i.b a2 = a(context, attributeSet, i2, i3);
        b(a2.a);
        b(a2.c);
        a(a2.d);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public RecyclerView.LayoutParams a() {
        return new RecyclerView.LayoutParams(-2, -2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void a(RecyclerView recyclerView, RecyclerView.o oVar) {
        super.a(recyclerView, oVar);
        if (this.f) {
            c(oVar);
            oVar.a();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void a(AccessibilityEvent accessibilityEvent) {
        super.a(accessibilityEvent);
        if (v() > 0) {
            accessibilityEvent.setFromIndex(m());
            accessibilityEvent.setToIndex(n());
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public Parcelable d() {
        SavedState savedState = this.n;
        if (savedState != null) {
            return new SavedState(savedState);
        }
        SavedState savedState2 = new SavedState();
        if (v() > 0) {
            i();
            boolean z = this.b ^ this.k;
            savedState2.c = z;
            if (z) {
                View M = M();
                savedState2.b = this.j.d() - this.j.b(M);
                savedState2.a = d(M);
            } else {
                View L = L();
                savedState2.a = d(L);
                savedState2.b = this.j.a(L) - this.j.c();
            }
        } else {
            savedState2.b();
        }
        return savedState2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void a(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            this.n = (SavedState) parcelable;
            o();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public boolean e() {
        return this.i == 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public boolean f() {
        return this.i == 1;
    }

    public void a(boolean z) {
        a((String) null);
        if (this.d != z) {
            this.d = z;
            o();
        }
    }

    public int g() {
        return this.i;
    }

    public void b(int i2) {
        if (i2 == 0 || i2 == 1) {
            a((String) null);
            if (i2 != this.i || this.j == null) {
                this.j = i.a(this, i2);
                this.o.a = this.j;
                this.i = i2;
                o();
                return;
            }
            return;
        }
        throw new IllegalArgumentException("invalid orientation:" + i2);
    }

    private void K() {
        if (this.i == 1 || !h()) {
            this.k = this.c;
        } else {
            this.k = !this.c;
        }
    }

    public void b(boolean z) {
        a((String) null);
        if (z != this.c) {
            this.c = z;
            o();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public View c(int i2) {
        int v = v();
        if (v == 0) {
            return null;
        }
        int d2 = i2 - d(i(0));
        if (d2 >= 0 && d2 < v) {
            View i3 = i(d2);
            if (d(i3) == i2) {
                return i3;
            }
        }
        return super.c(i2);
    }

    /* access modifiers changed from: protected */
    public int b(RecyclerView.s sVar) {
        if (sVar.d()) {
            return this.j.f();
        }
        return 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void a(RecyclerView recyclerView, RecyclerView.s sVar, int i2) {
        g gVar = new g(recyclerView.getContext());
        gVar.c(i2);
        a(gVar);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.r.b
    public PointF d(int i2) {
        if (v() == 0) {
            return null;
        }
        boolean z = false;
        int i3 = 1;
        if (i2 < d(i(0))) {
            z = true;
        }
        if (z != this.k) {
            i3 = -1;
        }
        if (this.i == 0) {
            return new PointF((float) i3, 0.0f);
        }
        return new PointF(0.0f, (float) i3);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void c(RecyclerView.o oVar, RecyclerView.s sVar) {
        int i2;
        int i3;
        int i4;
        int i5;
        View c2;
        int i6;
        int i7 = -1;
        if (!(this.n == null && this.l == -1) && sVar.e() == 0) {
            c(oVar);
            return;
        }
        SavedState savedState = this.n;
        if (savedState != null && savedState.a()) {
            this.l = this.n.a;
        }
        i();
        this.a.a = false;
        K();
        View E = E();
        if (!this.o.e || this.l != -1 || this.n != null) {
            this.o.a();
            a aVar = this.o;
            aVar.d = this.k ^ this.d;
            a(oVar, sVar, aVar);
            this.o.e = true;
        } else if (E != null && (this.j.a(E) >= this.j.d() || this.j.b(E) <= this.j.c())) {
            this.o.a(E, d(E));
        }
        int b2 = b(sVar);
        if (this.a.j >= 0) {
            i2 = b2;
            b2 = 0;
        } else {
            i2 = 0;
        }
        int c3 = b2 + this.j.c();
        int g2 = i2 + this.j.g();
        if (!(!sVar.a() || (i5 = this.l) == -1 || this.m == Integer.MIN_VALUE || (c2 = c(i5)) == null)) {
            if (this.k) {
                i6 = (this.j.d() - this.j.b(c2)) - this.m;
            } else {
                i6 = this.m - (this.j.a(c2) - this.j.c());
            }
            if (i6 > 0) {
                c3 += i6;
            } else {
                g2 -= i6;
            }
        }
        if (this.o.d) {
            if (this.k) {
                i7 = 1;
            }
        } else if (!this.k) {
            i7 = 1;
        }
        a(oVar, sVar, this.o, i7);
        a(oVar);
        this.a.l = k();
        this.a.i = sVar.a();
        if (this.o.d) {
            b(this.o);
            c cVar = this.a;
            cVar.h = c3;
            a(oVar, cVar, sVar, false);
            i4 = this.a.b;
            int i8 = this.a.d;
            if (this.a.c > 0) {
                g2 += this.a.c;
            }
            a(this.o);
            c cVar2 = this.a;
            cVar2.h = g2;
            cVar2.d += this.a.e;
            a(oVar, this.a, sVar, false);
            i3 = this.a.b;
            if (this.a.c > 0) {
                int i9 = this.a.c;
                g(i8, i4);
                c cVar3 = this.a;
                cVar3.h = i9;
                a(oVar, cVar3, sVar, false);
                i4 = this.a.b;
            }
        } else {
            a(this.o);
            c cVar4 = this.a;
            cVar4.h = g2;
            a(oVar, cVar4, sVar, false);
            i3 = this.a.b;
            int i10 = this.a.d;
            if (this.a.c > 0) {
                c3 += this.a.c;
            }
            b(this.o);
            c cVar5 = this.a;
            cVar5.h = c3;
            cVar5.d += this.a.e;
            a(oVar, this.a, sVar, false);
            i4 = this.a.b;
            if (this.a.c > 0) {
                int i11 = this.a.c;
                a(i10, i3);
                c cVar6 = this.a;
                cVar6.h = i11;
                a(oVar, cVar6, sVar, false);
                i3 = this.a.b;
            }
        }
        if (v() > 0) {
            if (this.k ^ this.d) {
                int a2 = a(i3, oVar, sVar, true);
                int i12 = i4 + a2;
                int i13 = i3 + a2;
                int b3 = b(i12, oVar, sVar, false);
                i4 = i12 + b3;
                i3 = i13 + b3;
            } else {
                int b4 = b(i4, oVar, sVar, true);
                int i14 = i4 + b4;
                int i15 = i3 + b4;
                int a3 = a(i15, oVar, sVar, false);
                i4 = i14 + a3;
                i3 = i15 + a3;
            }
        }
        b(oVar, sVar, i4, i3);
        if (!sVar.a()) {
            this.j.a();
        } else {
            this.o.a();
        }
        this.b = this.d;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void a(RecyclerView.s sVar) {
        super.a(sVar);
        this.n = null;
        this.l = -1;
        this.m = androidx.customview.a.a.INVALID_ID;
        this.o.a();
    }

    private void b(RecyclerView.o oVar, RecyclerView.s sVar, int i2, int i3) {
        if (sVar.b() && v() != 0 && !sVar.a() && b()) {
            List<RecyclerView.v> c2 = oVar.c();
            int size = c2.size();
            int d2 = d(i(0));
            int i4 = 0;
            int i5 = 0;
            for (int i6 = 0; i6 < size; i6++) {
                RecyclerView.v vVar = c2.get(i6);
                if (!vVar.isRemoved()) {
                    char c3 = 1;
                    if ((vVar.getLayoutPosition() < d2) != this.k) {
                        c3 = 65535;
                    }
                    if (c3 == 65535) {
                        i4 += this.j.e(vVar.itemView);
                    } else {
                        i5 += this.j.e(vVar.itemView);
                    }
                }
            }
            this.a.k = c2;
            if (i4 > 0) {
                g(d(L()), i2);
                c cVar = this.a;
                cVar.h = i4;
                cVar.c = 0;
                cVar.a();
                a(oVar, this.a, sVar, false);
            }
            if (i5 > 0) {
                a(d(M()), i3);
                c cVar2 = this.a;
                cVar2.h = i5;
                cVar2.c = 0;
                cVar2.a();
                a(oVar, this.a, sVar, false);
            }
            this.a.k = null;
        }
    }

    private void a(RecyclerView.o oVar, RecyclerView.s sVar, a aVar) {
        if (!a(sVar, aVar) && !b(oVar, sVar, aVar)) {
            aVar.b();
            aVar.b = this.d ? sVar.e() - 1 : 0;
        }
    }

    private boolean b(RecyclerView.o oVar, RecyclerView.s sVar, a aVar) {
        View view;
        int i2;
        boolean z = false;
        if (v() == 0) {
            return false;
        }
        View E = E();
        if (E != null && aVar.a(E, sVar)) {
            aVar.a(E, d(E));
            return true;
        } else if (this.b != this.d) {
            return false;
        } else {
            if (aVar.d) {
                view = f(oVar, sVar);
            } else {
                view = g(oVar, sVar);
            }
            if (view == null) {
                return false;
            }
            aVar.b(view, d(view));
            if (!sVar.a() && b()) {
                if (this.j.a(view) >= this.j.d() || this.j.b(view) < this.j.c()) {
                    z = true;
                }
                if (z) {
                    if (aVar.d) {
                        i2 = this.j.d();
                    } else {
                        i2 = this.j.c();
                    }
                    aVar.c = i2;
                }
            }
            return true;
        }
    }

    private boolean a(RecyclerView.s sVar, a aVar) {
        int i2;
        int i3;
        boolean z = false;
        if (sVar.a() || (i2 = this.l) == -1) {
            return false;
        }
        if (i2 < 0 || i2 >= sVar.e()) {
            this.l = -1;
            this.m = androidx.customview.a.a.INVALID_ID;
            return false;
        }
        aVar.b = this.l;
        SavedState savedState = this.n;
        if (savedState != null && savedState.a()) {
            aVar.d = this.n.c;
            if (aVar.d) {
                aVar.c = this.j.d() - this.n.b;
            } else {
                aVar.c = this.j.c() + this.n.b;
            }
            return true;
        } else if (this.m == Integer.MIN_VALUE) {
            View c2 = c(this.l);
            if (c2 == null) {
                if (v() > 0) {
                    if ((this.l < d(i(0))) == this.k) {
                        z = true;
                    }
                    aVar.d = z;
                }
                aVar.b();
            } else if (this.j.e(c2) > this.j.f()) {
                aVar.b();
                return true;
            } else if (this.j.a(c2) - this.j.c() < 0) {
                aVar.c = this.j.c();
                aVar.d = false;
                return true;
            } else if (this.j.d() - this.j.b(c2) < 0) {
                aVar.c = this.j.d();
                aVar.d = true;
                return true;
            } else {
                if (aVar.d) {
                    i3 = this.j.b(c2) + this.j.b();
                } else {
                    i3 = this.j.a(c2);
                }
                aVar.c = i3;
            }
            return true;
        } else {
            boolean z2 = this.k;
            aVar.d = z2;
            if (z2) {
                aVar.c = this.j.d() - this.m;
            } else {
                aVar.c = this.j.c() + this.m;
            }
            return true;
        }
    }

    private int a(int i2, RecyclerView.o oVar, RecyclerView.s sVar, boolean z) {
        int d2;
        int d3 = this.j.d() - i2;
        if (d3 <= 0) {
            return 0;
        }
        int i3 = -c(-d3, oVar, sVar);
        int i4 = i2 + i3;
        if (!z || (d2 = this.j.d() - i4) <= 0) {
            return i3;
        }
        this.j.a(d2);
        return d2 + i3;
    }

    private int b(int i2, RecyclerView.o oVar, RecyclerView.s sVar, boolean z) {
        int c2;
        int c3 = i2 - this.j.c();
        if (c3 <= 0) {
            return 0;
        }
        int i3 = -c(c3, oVar, sVar);
        int i4 = i2 + i3;
        if (!z || (c2 = i4 - this.j.c()) <= 0) {
            return i3;
        }
        this.j.a(-c2);
        return i3 - c2;
    }

    private void a(a aVar) {
        a(aVar.b, aVar.c);
    }

    private void a(int i2, int i3) {
        this.a.c = this.j.d() - i3;
        this.a.e = this.k ? -1 : 1;
        c cVar = this.a;
        cVar.d = i2;
        cVar.f = 1;
        cVar.b = i3;
        cVar.g = androidx.customview.a.a.INVALID_ID;
    }

    private void b(a aVar) {
        g(aVar.b, aVar.c);
    }

    private void g(int i2, int i3) {
        this.a.c = i3 - this.j.c();
        c cVar = this.a;
        cVar.d = i2;
        cVar.e = this.k ? 1 : -1;
        c cVar2 = this.a;
        cVar2.f = -1;
        cVar2.b = i3;
        cVar2.g = androidx.customview.a.a.INVALID_ID;
    }

    /* access modifiers changed from: protected */
    public boolean h() {
        return t() == 1;
    }

    /* access modifiers changed from: package-private */
    public void i() {
        if (this.a == null) {
            this.a = j();
        }
    }

    /* access modifiers changed from: package-private */
    public c j() {
        return new c();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void e(int i2) {
        this.l = i2;
        this.m = androidx.customview.a.a.INVALID_ID;
        SavedState savedState = this.n;
        if (savedState != null) {
            savedState.b();
        }
        o();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public int a(int i2, RecyclerView.o oVar, RecyclerView.s sVar) {
        if (this.i == 1) {
            return 0;
        }
        return c(i2, oVar, sVar);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public int b(int i2, RecyclerView.o oVar, RecyclerView.s sVar) {
        if (this.i == 0) {
            return 0;
        }
        return c(i2, oVar, sVar);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public int c(RecyclerView.s sVar) {
        return i(sVar);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public int d(RecyclerView.s sVar) {
        return i(sVar);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public int e(RecyclerView.s sVar) {
        return j(sVar);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public int f(RecyclerView.s sVar) {
        return j(sVar);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public int g(RecyclerView.s sVar) {
        return k(sVar);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public int h(RecyclerView.s sVar) {
        return k(sVar);
    }

    private int i(RecyclerView.s sVar) {
        if (v() == 0) {
            return 0;
        }
        i();
        return k.a(sVar, this.j, a(!this.e, true), b(!this.e, true), this, this.e, this.k);
    }

    private int j(RecyclerView.s sVar) {
        if (v() == 0) {
            return 0;
        }
        i();
        return k.a(sVar, this.j, a(!this.e, true), b(!this.e, true), this, this.e);
    }

    private int k(RecyclerView.s sVar) {
        if (v() == 0) {
            return 0;
        }
        i();
        return k.b(sVar, this.j, a(!this.e, true), b(!this.e, true), this, this.e);
    }

    private void a(int i2, int i3, boolean z, RecyclerView.s sVar) {
        int i4;
        this.a.l = k();
        this.a.h = b(sVar);
        c cVar = this.a;
        cVar.f = i2;
        int i5 = -1;
        if (i2 == 1) {
            cVar.h += this.j.g();
            View M = M();
            c cVar2 = this.a;
            if (!this.k) {
                i5 = 1;
            }
            cVar2.e = i5;
            this.a.d = d(M) + this.a.e;
            this.a.b = this.j.b(M);
            i4 = this.j.b(M) - this.j.d();
        } else {
            View L = L();
            this.a.h += this.j.c();
            c cVar3 = this.a;
            if (this.k) {
                i5 = 1;
            }
            cVar3.e = i5;
            this.a.d = d(L) + this.a.e;
            this.a.b = this.j.a(L);
            i4 = (-this.j.a(L)) + this.j.c();
        }
        c cVar4 = this.a;
        cVar4.c = i3;
        if (z) {
            cVar4.c -= i4;
        }
        this.a.g = i4;
    }

    /* access modifiers changed from: package-private */
    public boolean k() {
        return this.j.h() == 0 && this.j.e() == 0;
    }

    /* access modifiers changed from: package-private */
    public void a(RecyclerView.s sVar, c cVar, RecyclerView.i.a aVar) {
        int i2 = cVar.d;
        if (i2 >= 0 && i2 < sVar.e()) {
            aVar.b(i2, Math.max(0, cVar.g));
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void a(int i2, RecyclerView.i.a aVar) {
        int i3;
        boolean z;
        SavedState savedState = this.n;
        int i4 = -1;
        if (savedState == null || !savedState.a()) {
            K();
            z = this.k;
            i3 = this.l;
            if (i3 == -1) {
                i3 = z ? i2 - 1 : 0;
            }
        } else {
            z = this.n.c;
            i3 = this.n.a;
        }
        if (!z) {
            i4 = 1;
        }
        for (int i5 = 0; i5 < this.h && i3 >= 0 && i3 < i2; i5++) {
            aVar.b(i3, 0);
            i3 += i4;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void a(int i2, int i3, RecyclerView.s sVar, RecyclerView.i.a aVar) {
        if (this.i != 0) {
            i2 = i3;
        }
        if (v() != 0 && i2 != 0) {
            i();
            a(i2 > 0 ? 1 : -1, Math.abs(i2), true, sVar);
            a(sVar, this.a, aVar);
        }
    }

    /* access modifiers changed from: package-private */
    public int c(int i2, RecyclerView.o oVar, RecyclerView.s sVar) {
        if (v() == 0 || i2 == 0) {
            return 0;
        }
        this.a.a = true;
        i();
        int i3 = i2 > 0 ? 1 : -1;
        int abs = Math.abs(i2);
        a(i3, abs, true, sVar);
        int a2 = this.a.g + a(oVar, this.a, sVar, false);
        if (a2 < 0) {
            return 0;
        }
        if (abs > a2) {
            i2 = i3 * a2;
        }
        this.j.a(-i2);
        this.a.j = i2;
        return i2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void a(String str) {
        if (this.n == null) {
            super.a(str);
        }
    }

    private void a(RecyclerView.o oVar, int i2, int i3) {
        if (i2 != i3) {
            if (i3 > i2) {
                for (int i4 = i3 - 1; i4 >= i2; i4--) {
                    a(i4, oVar);
                }
                return;
            }
            while (i2 > i3) {
                a(i2, oVar);
                i2--;
            }
        }
    }

    private void a(RecyclerView.o oVar, int i2) {
        if (i2 >= 0) {
            int v = v();
            if (this.k) {
                int i3 = v - 1;
                for (int i4 = i3; i4 >= 0; i4--) {
                    View i5 = i(i4);
                    if (this.j.b(i5) > i2 || this.j.c(i5) > i2) {
                        a(oVar, i3, i4);
                        return;
                    }
                }
                return;
            }
            for (int i6 = 0; i6 < v; i6++) {
                View i7 = i(i6);
                if (this.j.b(i7) > i2 || this.j.c(i7) > i2) {
                    a(oVar, 0, i6);
                    return;
                }
            }
        }
    }

    private void b(RecyclerView.o oVar, int i2) {
        int v = v();
        if (i2 >= 0) {
            int e2 = this.j.e() - i2;
            if (this.k) {
                for (int i3 = 0; i3 < v; i3++) {
                    View i4 = i(i3);
                    if (this.j.a(i4) < e2 || this.j.d(i4) < e2) {
                        a(oVar, 0, i3);
                        return;
                    }
                }
                return;
            }
            int i5 = v - 1;
            for (int i6 = i5; i6 >= 0; i6--) {
                View i7 = i(i6);
                if (this.j.a(i7) < e2 || this.j.d(i7) < e2) {
                    a(oVar, i5, i6);
                    return;
                }
            }
        }
    }

    private void a(RecyclerView.o oVar, c cVar) {
        if (cVar.a && !cVar.l) {
            if (cVar.f == -1) {
                b(oVar, cVar.g);
            } else {
                a(oVar, cVar.g);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public int a(RecyclerView.o oVar, c cVar, RecyclerView.s sVar, boolean z) {
        int i2 = cVar.c;
        if (cVar.g != Integer.MIN_VALUE) {
            if (cVar.c < 0) {
                cVar.g += cVar.c;
            }
            a(oVar, cVar);
        }
        int i3 = cVar.c + cVar.h;
        b bVar = this.g;
        while (true) {
            if ((!cVar.l && i3 <= 0) || !cVar.a(sVar)) {
                break;
            }
            bVar.a();
            a(oVar, sVar, cVar, bVar);
            if (!bVar.b) {
                cVar.b += bVar.a * cVar.f;
                if (!bVar.c || this.a.k != null || !sVar.a()) {
                    cVar.c -= bVar.a;
                    i3 -= bVar.a;
                }
                if (cVar.g != Integer.MIN_VALUE) {
                    cVar.g += bVar.a;
                    if (cVar.c < 0) {
                        cVar.g += cVar.c;
                    }
                    a(oVar, cVar);
                }
                if (z && bVar.d) {
                    break;
                }
            } else {
                break;
            }
        }
        return i2 - cVar.c;
    }

    /* access modifiers changed from: package-private */
    public void a(RecyclerView.o oVar, RecyclerView.s sVar, c cVar, b bVar) {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        View a2 = cVar.a(oVar);
        if (a2 == null) {
            bVar.b = true;
            return;
        }
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) a2.getLayoutParams();
        if (cVar.k == null) {
            if (this.k == (cVar.f == -1)) {
                b(a2);
            } else {
                b(a2, 0);
            }
        } else {
            if (this.k == (cVar.f == -1)) {
                a(a2);
            } else {
                a(a2, 0);
            }
        }
        a(a2, 0, 0);
        bVar.a = this.j.e(a2);
        if (this.i == 1) {
            if (h()) {
                i6 = y() - C();
                i5 = i6 - this.j.f(a2);
            } else {
                i5 = A();
                i6 = this.j.f(a2) + i5;
            }
            if (cVar.f == -1) {
                int i7 = cVar.b;
                i4 = cVar.b - bVar.a;
                i3 = i6;
                i2 = i7;
            } else {
                int i8 = cVar.b;
                i2 = cVar.b + bVar.a;
                i3 = i6;
                i4 = i8;
            }
        } else {
            int B = B();
            int f2 = this.j.f(a2) + B;
            if (cVar.f == -1) {
                i4 = B;
                i3 = cVar.b;
                i2 = f2;
                i5 = cVar.b - bVar.a;
            } else {
                int i9 = cVar.b;
                i3 = cVar.b + bVar.a;
                i4 = B;
                i2 = f2;
                i5 = i9;
            }
        }
        a(a2, i5, i4, i3, i2);
        if (layoutParams.d() || layoutParams.e()) {
            bVar.c = true;
        }
        bVar.d = a2.hasFocusable();
    }

    /* access modifiers changed from: package-private */
    @Override // androidx.recyclerview.widget.RecyclerView.i
    public boolean l() {
        return (x() == 1073741824 || w() == 1073741824 || !J()) ? false : true;
    }

    /* access modifiers changed from: package-private */
    public int f(int i2) {
        if (i2 != 17) {
            if (i2 != 33) {
                if (i2 != 66) {
                    if (i2 != 130) {
                        switch (i2) {
                            case 1:
                                return (this.i != 1 && h()) ? 1 : -1;
                            case 2:
                                return (this.i != 1 && h()) ? -1 : 1;
                            default:
                                return androidx.customview.a.a.INVALID_ID;
                        }
                    } else if (this.i == 1) {
                        return 1;
                    } else {
                        return androidx.customview.a.a.INVALID_ID;
                    }
                } else if (this.i == 0) {
                    return 1;
                } else {
                    return androidx.customview.a.a.INVALID_ID;
                }
            } else if (this.i == 1) {
                return -1;
            } else {
                return androidx.customview.a.a.INVALID_ID;
            }
        } else if (this.i == 0) {
            return -1;
        } else {
            return androidx.customview.a.a.INVALID_ID;
        }
    }

    private View L() {
        return i(this.k ? v() - 1 : 0);
    }

    private View M() {
        return i(this.k ? 0 : v() - 1);
    }

    private View a(boolean z, boolean z2) {
        if (this.k) {
            return a(v() - 1, -1, z, z2);
        }
        return a(0, v(), z, z2);
    }

    private View b(boolean z, boolean z2) {
        if (this.k) {
            return a(0, v(), z, z2);
        }
        return a(v() - 1, -1, z, z2);
    }

    private View f(RecyclerView.o oVar, RecyclerView.s sVar) {
        if (this.k) {
            return h(oVar, sVar);
        }
        return i(oVar, sVar);
    }

    private View g(RecyclerView.o oVar, RecyclerView.s sVar) {
        if (this.k) {
            return i(oVar, sVar);
        }
        return h(oVar, sVar);
    }

    private View h(RecyclerView.o oVar, RecyclerView.s sVar) {
        return a(oVar, sVar, 0, v(), sVar.e());
    }

    private View i(RecyclerView.o oVar, RecyclerView.s sVar) {
        return a(oVar, sVar, v() - 1, -1, sVar.e());
    }

    /* access modifiers changed from: package-private */
    public View a(RecyclerView.o oVar, RecyclerView.s sVar, int i2, int i3, int i4) {
        i();
        int c2 = this.j.c();
        int d2 = this.j.d();
        int i5 = i3 > i2 ? 1 : -1;
        View view = null;
        View view2 = null;
        while (i2 != i3) {
            View i6 = i(i2);
            int d3 = d(i6);
            if (d3 >= 0 && d3 < i4) {
                if (((RecyclerView.LayoutParams) i6.getLayoutParams()).d()) {
                    if (view2 == null) {
                        view2 = i6;
                    }
                } else if (this.j.a(i6) < d2 && this.j.b(i6) >= c2) {
                    return i6;
                } else {
                    if (view == null) {
                        view = i6;
                    }
                }
            }
            i2 += i5;
        }
        return view != null ? view : view2;
    }

    private View j(RecyclerView.o oVar, RecyclerView.s sVar) {
        if (this.k) {
            return l(oVar, sVar);
        }
        return m(oVar, sVar);
    }

    private View k(RecyclerView.o oVar, RecyclerView.s sVar) {
        if (this.k) {
            return m(oVar, sVar);
        }
        return l(oVar, sVar);
    }

    private View l(RecyclerView.o oVar, RecyclerView.s sVar) {
        return b(0, v());
    }

    private View m(RecyclerView.o oVar, RecyclerView.s sVar) {
        return b(v() - 1, -1);
    }

    public int m() {
        View a2 = a(0, v(), false, true);
        if (a2 == null) {
            return -1;
        }
        return d(a2);
    }

    public int n() {
        View a2 = a(v() - 1, -1, false, true);
        if (a2 == null) {
            return -1;
        }
        return d(a2);
    }

    /* access modifiers changed from: package-private */
    public View a(int i2, int i3, boolean z, boolean z2) {
        i();
        int i4 = 320;
        int i5 = z ? 24579 : 320;
        if (!z2) {
            i4 = 0;
        }
        if (this.i == 0) {
            return this.r.a(i2, i3, i5, i4);
        }
        return this.s.a(i2, i3, i5, i4);
    }

    /* access modifiers changed from: package-private */
    public View b(int i2, int i3) {
        int i4;
        int i5;
        i();
        if ((i3 > i2 ? 1 : i3 < i2 ? (char) 65535 : 0) == 0) {
            return i(i2);
        }
        if (this.j.a(i(i2)) < this.j.c()) {
            i5 = 16644;
            i4 = 16388;
        } else {
            i5 = 4161;
            i4 = 4097;
        }
        if (this.i == 0) {
            return this.r.a(i2, i3, i5, i4);
        }
        return this.s.a(i2, i3, i5, i4);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public View a(View view, int i2, RecyclerView.o oVar, RecyclerView.s sVar) {
        int f2;
        View view2;
        View view3;
        K();
        if (v() == 0 || (f2 = f(i2)) == Integer.MIN_VALUE) {
            return null;
        }
        i();
        i();
        a(f2, (int) (((float) this.j.f()) * 0.33333334f), false, sVar);
        c cVar = this.a;
        cVar.g = androidx.customview.a.a.INVALID_ID;
        cVar.a = false;
        a(oVar, cVar, sVar, true);
        if (f2 == -1) {
            view2 = k(oVar, sVar);
        } else {
            view2 = j(oVar, sVar);
        }
        if (f2 == -1) {
            view3 = L();
        } else {
            view3 = M();
        }
        if (!view3.hasFocusable()) {
            return view2;
        }
        if (view2 == null) {
            return null;
        }
        return view3;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public boolean b() {
        return this.n == null && this.b == this.d;
    }

    /* access modifiers changed from: package-private */
    public static class c {
        boolean a = true;
        int b;
        int c;
        int d;
        int e;
        int f;
        int g;
        int h = 0;
        boolean i = false;
        int j;
        List<RecyclerView.v> k = null;
        boolean l;

        c() {
        }

        /* access modifiers changed from: package-private */
        public boolean a(RecyclerView.s sVar) {
            int i2 = this.d;
            return i2 >= 0 && i2 < sVar.e();
        }

        /* access modifiers changed from: package-private */
        public View a(RecyclerView.o oVar) {
            if (this.k != null) {
                return b();
            }
            View c2 = oVar.c(this.d);
            this.d += this.e;
            return c2;
        }

        private View b() {
            int size = this.k.size();
            for (int i2 = 0; i2 < size; i2++) {
                View view = this.k.get(i2).itemView;
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
                if (!layoutParams.d() && this.d == layoutParams.f()) {
                    a(view);
                    return view;
                }
            }
            return null;
        }

        public void a() {
            a((View) null);
        }

        public void a(View view) {
            View b2 = b(view);
            if (b2 == null) {
                this.d = -1;
            } else {
                this.d = ((RecyclerView.LayoutParams) b2.getLayoutParams()).f();
            }
        }

        public View b(View view) {
            int f2;
            int size = this.k.size();
            View view2 = null;
            int i2 = Api.BaseClientBuilder.API_PRIORITY_OTHER;
            for (int i3 = 0; i3 < size; i3++) {
                View view3 = this.k.get(i3).itemView;
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view3.getLayoutParams();
                if (view3 != view && !layoutParams.d() && (f2 = (layoutParams.f() - this.d) * this.e) >= 0 && f2 < i2) {
                    if (f2 == 0) {
                        return view3;
                    }
                    view2 = view3;
                    i2 = f2;
                }
            }
            return view2;
        }
    }

    public static class SavedState implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            /* class androidx.recyclerview.widget.LinearLayoutManager.SavedState.AnonymousClass1 */

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
        boolean c;

        public int describeContents() {
            return 0;
        }

        public SavedState() {
        }

        SavedState(Parcel parcel) {
            this.a = parcel.readInt();
            this.b = parcel.readInt();
            this.c = parcel.readInt() != 1 ? false : true;
        }

        public SavedState(SavedState savedState) {
            this.a = savedState.a;
            this.b = savedState.b;
            this.c = savedState.c;
        }

        /* access modifiers changed from: package-private */
        public boolean a() {
            return this.a >= 0;
        }

        /* access modifiers changed from: package-private */
        public void b() {
            this.a = -1;
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.a);
            parcel.writeInt(this.b);
            parcel.writeInt(this.c ? 1 : 0);
        }
    }

    /* access modifiers changed from: package-private */
    public static class a {
        i a;
        int b;
        int c;
        boolean d;
        boolean e;

        a() {
            a();
        }

        /* access modifiers changed from: package-private */
        public void a() {
            this.b = -1;
            this.c = androidx.customview.a.a.INVALID_ID;
            this.d = false;
            this.e = false;
        }

        /* access modifiers changed from: package-private */
        public void b() {
            int i;
            if (this.d) {
                i = this.a.d();
            } else {
                i = this.a.c();
            }
            this.c = i;
        }

        public String toString() {
            return "AnchorInfo{mPosition=" + this.b + ", mCoordinate=" + this.c + ", mLayoutFromEnd=" + this.d + ", mValid=" + this.e + JSONTranscoder.OBJ_END;
        }

        /* access modifiers changed from: package-private */
        public boolean a(View view, RecyclerView.s sVar) {
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
            return !layoutParams.d() && layoutParams.f() >= 0 && layoutParams.f() < sVar.e();
        }

        public void a(View view, int i) {
            int b2 = this.a.b();
            if (b2 >= 0) {
                b(view, i);
                return;
            }
            this.b = i;
            if (this.d) {
                int d2 = (this.a.d() - b2) - this.a.b(view);
                this.c = this.a.d() - d2;
                if (d2 > 0) {
                    int e2 = this.c - this.a.e(view);
                    int c2 = this.a.c();
                    int min = e2 - (c2 + Math.min(this.a.a(view) - c2, 0));
                    if (min < 0) {
                        this.c += Math.min(d2, -min);
                        return;
                    }
                    return;
                }
                return;
            }
            int a2 = this.a.a(view);
            int c3 = a2 - this.a.c();
            this.c = a2;
            if (c3 > 0) {
                int d3 = (this.a.d() - Math.min(0, (this.a.d() - b2) - this.a.b(view))) - (a2 + this.a.e(view));
                if (d3 < 0) {
                    this.c -= Math.min(c3, -d3);
                }
            }
        }

        public void b(View view, int i) {
            if (this.d) {
                this.c = this.a.b(view) + this.a.b();
            } else {
                this.c = this.a.a(view);
            }
            this.b = i;
        }
    }

    /* access modifiers changed from: protected */
    public static class b {
        public int a;
        public boolean b;
        public boolean c;
        public boolean d;

        protected b() {
        }

        /* access modifiers changed from: package-private */
        public void a() {
            this.a = 0;
            this.b = false;
            this.c = false;
            this.d = false;
        }
    }
}
