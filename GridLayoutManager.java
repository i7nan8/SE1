package androidx.recyclerview.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.f.a.c;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Arrays;

public class GridLayoutManager extends LinearLayoutManager {
    boolean a = false;
    int b = -1;
    int[] c;
    View[] d;
    final SparseIntArray e = new SparseIntArray();
    final SparseIntArray f = new SparseIntArray();
    b g = new a();
    final Rect h = new Rect();

    public GridLayoutManager(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        a(a(context, attributeSet, i, i2).b);
    }

    public GridLayoutManager(Context context, int i) {
        super(context);
        a(i);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager
    public void a(boolean z) {
        if (!z) {
            super.a(false);
            return;
        }
        throw new UnsupportedOperationException("GridLayoutManager does not support stack from end. Consider using reverse layout");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public int a(RecyclerView.o oVar, RecyclerView.s sVar) {
        if (this.i == 0) {
            return this.b;
        }
        if (sVar.e() < 1) {
            return 0;
        }
        return a(oVar, sVar, sVar.e() - 1) + 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public int b(RecyclerView.o oVar, RecyclerView.s sVar) {
        if (this.i == 1) {
            return this.b;
        }
        if (sVar.e() < 1) {
            return 0;
        }
        return a(oVar, sVar, sVar.e() - 1) + 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void a(RecyclerView.o oVar, RecyclerView.s sVar, View view, c cVar) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (!(layoutParams instanceof LayoutParams)) {
            super.a(view, cVar);
            return;
        }
        LayoutParams layoutParams2 = (LayoutParams) layoutParams;
        int a2 = a(oVar, sVar, layoutParams2.f());
        if (this.i == 0) {
            cVar.b(c.C٠٠٣٢c.a(layoutParams2.a(), layoutParams2.b(), a2, 1, this.b > 1 && layoutParams2.b() == this.b, false));
        } else {
            cVar.b(c.C٠٠٣٢c.a(a2, 1, layoutParams2.a(), layoutParams2.b(), this.b > 1 && layoutParams2.b() == this.b, false));
        }
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.i
    public void c(RecyclerView.o oVar, RecyclerView.s sVar) {
        if (sVar.a()) {
            L();
        }
        super.c(oVar, sVar);
        K();
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.i
    public void a(RecyclerView.s sVar) {
        super.a(sVar);
        this.a = false;
    }

    private void K() {
        this.e.clear();
        this.f.clear();
    }

    private void L() {
        int v = v();
        for (int i = 0; i < v; i++) {
            LayoutParams layoutParams = (LayoutParams) i(i).getLayoutParams();
            int f2 = layoutParams.f();
            this.e.put(f2, layoutParams.b());
            this.f.put(f2, layoutParams.a());
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void a(RecyclerView recyclerView, int i, int i2) {
        this.g.a();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void a(RecyclerView recyclerView) {
        this.g.a();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void b(RecyclerView recyclerView, int i, int i2) {
        this.g.a();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void a(RecyclerView recyclerView, int i, int i2, Object obj) {
        this.g.a();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void a(RecyclerView recyclerView, int i, int i2, int i3) {
        this.g.a();
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.i
    public RecyclerView.LayoutParams a() {
        if (this.i == 0) {
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

    public void a(b bVar) {
        this.g = bVar;
    }

    private void M() {
        int i;
        if (g() == 1) {
            i = (y() - C()) - A();
        } else {
            i = (z() - D()) - B();
        }
        m(i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.i
    public void a(Rect rect, int i, int i2) {
        int i3;
        int i4;
        if (this.c == null) {
            super.a(rect, i, i2);
        }
        int A = A() + C();
        int B = B() + D();
        if (this.i == 1) {
            i4 = a(i2, rect.height() + B, G());
            int[] iArr = this.c;
            i3 = a(i, iArr[iArr.length - 1] + A, F());
        } else {
            i3 = a(i, rect.width() + A, F());
            int[] iArr2 = this.c;
            i4 = a(i2, iArr2[iArr2.length - 1] + B, G());
        }
        f(i3, i4);
    }

    private void m(int i) {
        this.c = a(this.c, this.b, i);
    }

    static int[] a(int[] iArr, int i, int i2) {
        int i3;
        if (!(iArr != null && iArr.length == i + 1 && iArr[iArr.length - 1] == i2)) {
            iArr = new int[(i + 1)];
        }
        int i4 = 0;
        iArr[0] = 0;
        int i5 = i2 / i;
        int i6 = i2 % i;
        int i7 = 0;
        for (int i8 = 1; i8 <= i; i8++) {
            i4 += i6;
            if (i4 <= 0 || i - i4 >= i6) {
                i3 = i5;
            } else {
                i3 = i5 + 1;
                i4 -= i;
            }
            i7 += i3;
            iArr[i8] = i7;
        }
        return iArr;
    }

    /* access modifiers changed from: package-private */
    public int a(int i, int i2) {
        if (this.i != 1 || !h()) {
            int[] iArr = this.c;
            return iArr[i2 + i] - iArr[i];
        }
        int[] iArr2 = this.c;
        int i3 = this.b;
        return iArr2[i3 - i] - iArr2[(i3 - i) - i2];
    }

    /* access modifiers changed from: package-private */
    @Override // androidx.recyclerview.widget.LinearLayoutManager
    public void a(RecyclerView.o oVar, RecyclerView.s sVar, LinearLayoutManager.a aVar, int i) {
        super.a(oVar, sVar, aVar, i);
        M();
        if (sVar.e() > 0 && !sVar.a()) {
            b(oVar, sVar, aVar, i);
        }
        N();
    }

    private void N() {
        View[] viewArr = this.d;
        if (viewArr == null || viewArr.length != this.b) {
            this.d = new View[this.b];
        }
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.i
    public int a(int i, RecyclerView.o oVar, RecyclerView.s sVar) {
        M();
        N();
        return super.a(i, oVar, sVar);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.i
    public int b(int i, RecyclerView.o oVar, RecyclerView.s sVar) {
        M();
        N();
        return super.b(i, oVar, sVar);
    }

    private void b(RecyclerView.o oVar, RecyclerView.s sVar, LinearLayoutManager.a aVar, int i) {
        boolean z = i == 1;
        int b2 = b(oVar, sVar, aVar.b);
        if (z) {
            while (b2 > 0 && aVar.b > 0) {
                aVar.b--;
                b2 = b(oVar, sVar, aVar.b);
            }
            return;
        }
        int e2 = sVar.e() - 1;
        int i2 = aVar.b;
        while (i2 < e2) {
            int i3 = i2 + 1;
            int b3 = b(oVar, sVar, i3);
            if (b3 <= b2) {
                break;
            }
            i2 = i3;
            b2 = b3;
        }
        aVar.b = i2;
    }

    /* access modifiers changed from: package-private */
    @Override // androidx.recyclerview.widget.LinearLayoutManager
    public View a(RecyclerView.o oVar, RecyclerView.s sVar, int i, int i2, int i3) {
        i();
        int c2 = this.j.c();
        int d2 = this.j.d();
        int i4 = i2 > i ? 1 : -1;
        View view = null;
        View view2 = null;
        while (i != i2) {
            View i5 = i(i);
            int d3 = d(i5);
            if (d3 >= 0 && d3 < i3 && b(oVar, sVar, d3) == 0) {
                if (((RecyclerView.LayoutParams) i5.getLayoutParams()).d()) {
                    if (view2 == null) {
                        view2 = i5;
                    }
                } else if (this.j.a(i5) < d2 && this.j.b(i5) >= c2) {
                    return i5;
                } else {
                    if (view == null) {
                        view = i5;
                    }
                }
            }
            i += i4;
        }
        return view != null ? view : view2;
    }

    private int a(RecyclerView.o oVar, RecyclerView.s sVar, int i) {
        if (!sVar.a()) {
            return this.g.c(i, this.b);
        }
        int b2 = oVar.b(i);
        if (b2 != -1) {
            return this.g.c(b2, this.b);
        }
        Log.w("GridLayoutManager", "Cannot find span size for pre layout position. " + i);
        return 0;
    }

    private int b(RecyclerView.o oVar, RecyclerView.s sVar, int i) {
        if (!sVar.a()) {
            return this.g.b(i, this.b);
        }
        int i2 = this.f.get(i, -1);
        if (i2 != -1) {
            return i2;
        }
        int b2 = oVar.b(i);
        if (b2 != -1) {
            return this.g.b(b2, this.b);
        }
        Log.w("GridLayoutManager", "Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:" + i);
        return 0;
    }

    private int c(RecyclerView.o oVar, RecyclerView.s sVar, int i) {
        if (!sVar.a()) {
            return this.g.a(i);
        }
        int i2 = this.e.get(i, -1);
        if (i2 != -1) {
            return i2;
        }
        int b2 = oVar.b(i);
        if (b2 != -1) {
            return this.g.a(b2);
        }
        Log.w("GridLayoutManager", "Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:" + i);
        return 1;
    }

    /* access modifiers changed from: package-private */
    @Override // androidx.recyclerview.widget.LinearLayoutManager
    public void a(RecyclerView.s sVar, LinearLayoutManager.c cVar, RecyclerView.i.a aVar) {
        int i = this.b;
        for (int i2 = 0; i2 < this.b && cVar.a(sVar) && i > 0; i2++) {
            int i3 = cVar.d;
            aVar.b(i3, Math.max(0, cVar.g));
            i -= this.g.a(i3);
            cVar.d += cVar.e;
        }
    }

    /* access modifiers changed from: package-private */
    @Override // androidx.recyclerview.widget.LinearLayoutManager
    public void a(RecyclerView.o oVar, RecyclerView.s sVar, LinearLayoutManager.c cVar, LinearLayoutManager.b bVar) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        boolean z;
        View a2;
        int i13 = this.j.i();
        boolean z2 = i13 != 1073741824;
        int i14 = v() > 0 ? this.c[this.b] : 0;
        if (z2) {
            M();
        }
        boolean z3 = cVar.e == 1;
        int i15 = this.b;
        if (!z3) {
            i15 = b(oVar, sVar, cVar.d) + c(oVar, sVar, cVar.d);
            i2 = 0;
            i = 0;
        } else {
            i2 = 0;
            i = 0;
        }
        while (i < this.b && cVar.a(sVar) && i15 > 0) {
            int i16 = cVar.d;
            int c2 = c(oVar, sVar, i16);
            if (c2 <= this.b) {
                i15 -= c2;
                if (i15 < 0 || (a2 = cVar.a(oVar)) == null) {
                    break;
                }
                i2 += c2;
                this.d[i] = a2;
                i++;
            } else {
                throw new IllegalArgumentException("Item at position " + i16 + " requires " + c2 + " spans but GridLayoutManager has only " + this.b + " spans.");
            }
        }
        if (i == 0) {
            bVar.b = true;
            return;
        }
        float f2 = 0.0f;
        a(oVar, sVar, i, i2, z3);
        int i17 = 0;
        for (int i18 = 0; i18 < i; i18++) {
            View view = this.d[i18];
            if (cVar.k != null) {
                z = false;
                if (z3) {
                    a(view);
                } else {
                    a(view, 0);
                }
            } else if (z3) {
                b(view);
                z = false;
            } else {
                z = false;
                b(view, 0);
            }
            b(view, this.h);
            a(view, i13, z);
            int e2 = this.j.e(view);
            if (e2 > i17) {
                i17 = e2;
            }
            float f3 = (((float) this.j.f(view)) * 1.0f) / ((float) ((LayoutParams) view.getLayoutParams()).b);
            if (f3 > f2) {
                f2 = f3;
            }
        }
        if (z2) {
            a(f2, i14);
            i17 = 0;
            for (int i19 = 0; i19 < i; i19++) {
                View view2 = this.d[i19];
                a(view2, 1073741824, true);
                int e3 = this.j.e(view2);
                if (e3 > i17) {
                    i17 = e3;
                }
            }
        }
        for (int i20 = 0; i20 < i; i20++) {
            View view3 = this.d[i20];
            if (this.j.e(view3) != i17) {
                LayoutParams layoutParams = (LayoutParams) view3.getLayoutParams();
                Rect rect = layoutParams.d;
                int i21 = rect.top + rect.bottom + layoutParams.topMargin + layoutParams.bottomMargin;
                int i22 = rect.left + rect.right + layoutParams.leftMargin + layoutParams.rightMargin;
                int a3 = a(layoutParams.a, layoutParams.b);
                if (this.i == 1) {
                    i12 = a(a3, 1073741824, i22, layoutParams.width, false);
                    i11 = View.MeasureSpec.makeMeasureSpec(i17 - i21, 1073741824);
                } else {
                    int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i17 - i22, 1073741824);
                    i11 = a(a3, 1073741824, i21, layoutParams.height, false);
                    i12 = makeMeasureSpec;
                }
                a(view3, i12, i11, true);
            }
        }
        int i23 = 0;
        bVar.a = i17;
        if (this.i == 1) {
            if (cVar.f == -1) {
                int i24 = cVar.b;
                i3 = i24;
                i4 = i24 - i17;
                i6 = 0;
                i5 = 0;
            } else {
                int i25 = cVar.b;
                i4 = i25;
                i3 = i17 + i25;
                i6 = 0;
                i5 = 0;
            }
        } else if (cVar.f == -1) {
            int i26 = cVar.b;
            int i27 = i26 - i17;
            i4 = 0;
            i3 = 0;
            i5 = i26;
            i6 = i27;
        } else {
            i6 = cVar.b;
            i5 = i17 + i6;
            i4 = 0;
            i3 = 0;
        }
        while (i23 < i) {
            View view4 = this.d[i23];
            LayoutParams layoutParams2 = (LayoutParams) view4.getLayoutParams();
            if (this.i != 1) {
                int B = B() + this.c[layoutParams2.a];
                i10 = i6;
                i8 = i5;
                i9 = B;
                i7 = this.j.f(view4) + B;
            } else if (h()) {
                int A = A() + this.c[this.b - layoutParams2.a];
                i8 = A;
                i10 = A - this.j.f(view4);
                i9 = i4;
                i7 = i3;
            } else {
                int A2 = A() + this.c[layoutParams2.a];
                i10 = A2;
                i8 = this.j.f(view4) + A2;
                i9 = i4;
                i7 = i3;
            }
            a(view4, i10, i9, i8, i7);
            if (layoutParams2.d() || layoutParams2.e()) {
                bVar.c = true;
            }
            bVar.d |= view4.hasFocusable();
            i23++;
            i6 = i10;
            i4 = i9;
            i5 = i8;
            i3 = i7;
        }
        Arrays.fill(this.d, (Object) null);
    }

    private void a(View view, int i, boolean z) {
        int i2;
        int i3;
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        Rect rect = layoutParams.d;
        int i4 = rect.top + rect.bottom + layoutParams.topMargin + layoutParams.bottomMargin;
        int i5 = rect.left + rect.right + layoutParams.leftMargin + layoutParams.rightMargin;
        int a2 = a(layoutParams.a, layoutParams.b);
        if (this.i == 1) {
            i2 = a(a2, i, i5, layoutParams.width, false);
            i3 = a(this.j.f(), x(), i4, layoutParams.height, true);
        } else {
            int a3 = a(a2, i, i4, layoutParams.height, false);
            int a4 = a(this.j.f(), w(), i5, layoutParams.width, true);
            i3 = a3;
            i2 = a4;
        }
        a(view, i2, i3, z);
    }

    private void a(float f2, int i) {
        m(Math.max(Math.round(f2 * ((float) this.b)), i));
    }

    private void a(View view, int i, int i2, boolean z) {
        boolean z2;
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        if (z) {
            z2 = a(view, i, i2, layoutParams);
        } else {
            z2 = b(view, i, i2, layoutParams);
        }
        if (z2) {
            view.measure(i, i2);
        }
    }

    private void a(RecyclerView.o oVar, RecyclerView.s sVar, int i, int i2, boolean z) {
        int i3;
        int i4;
        int i5 = 0;
        int i6 = -1;
        if (z) {
            i6 = i;
            i4 = 0;
            i3 = 1;
        } else {
            i4 = i - 1;
            i3 = -1;
        }
        while (i4 != i6) {
            View view = this.d[i4];
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            layoutParams.b = c(oVar, sVar, d(view));
            layoutParams.a = i5;
            i5 += layoutParams.b;
            i4 += i3;
        }
    }

    public void a(int i) {
        if (i != this.b) {
            this.a = true;
            if (i >= 1) {
                this.b = i;
                this.g.a();
                o();
                return;
            }
            throw new IllegalArgumentException("Span count should be at least 1. Provided " + i);
        }
    }

    public static abstract class b {
        final SparseIntArray a = new SparseIntArray();
        private boolean b = false;

        public abstract int a(int i);

        public void a() {
            this.a.clear();
        }

        /* access modifiers changed from: package-private */
        public int b(int i, int i2) {
            if (!this.b) {
                return a(i, i2);
            }
            int i3 = this.a.get(i, -1);
            if (i3 != -1) {
                return i3;
            }
            int a2 = a(i, i2);
            this.a.put(i, a2);
            return a2;
        }

        public int a(int i, int i2) {
            int i3;
            int i4;
            int b2;
            int a2 = a(i);
            if (a2 == i2) {
                return 0;
            }
            if (!this.b || this.a.size() <= 0 || (b2 = b(i)) < 0) {
                i4 = 0;
                i3 = 0;
            } else {
                i3 = this.a.get(b2) + a(b2);
                i4 = b2 + 1;
            }
            while (i4 < i) {
                int a3 = a(i4);
                i3 += a3;
                if (i3 == i2) {
                    i3 = 0;
                } else if (i3 > i2) {
                    i3 = a3;
                }
                i4++;
            }
            if (a2 + i3 <= i2) {
                return i3;
            }
            return 0;
        }

        /* access modifiers changed from: package-private */
        public int b(int i) {
            int size = this.a.size() - 1;
            int i2 = 0;
            while (i2 <= size) {
                int i3 = (i2 + size) >>> 1;
                if (this.a.keyAt(i3) < i) {
                    i2 = i3 + 1;
                } else {
                    size = i3 - 1;
                }
            }
            int i4 = i2 - 1;
            if (i4 < 0 || i4 >= this.a.size()) {
                return -1;
            }
            return this.a.keyAt(i4);
        }

        public int c(int i, int i2) {
            int a2 = a(i);
            int i3 = 0;
            int i4 = 0;
            for (int i5 = 0; i5 < i; i5++) {
                int a3 = a(i5);
                i3 += a3;
                if (i3 == i2) {
                    i4++;
                    i3 = 0;
                } else if (i3 > i2) {
                    i4++;
                    i3 = a3;
                }
            }
            return i3 + a2 > i2 ? i4 + 1 : i4;
        }
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.i
    public View a(View view, int i, RecyclerView.o oVar, RecyclerView.s sVar) {
        int i2;
        int i3;
        int i4;
        View view2;
        int i5;
        int i6;
        View view3;
        int i7;
        View view4;
        int i8;
        boolean z;
        RecyclerView.o oVar2 = oVar;
        RecyclerView.s sVar2 = sVar;
        View e2 = e(view);
        View view5 = null;
        if (e2 == null) {
            return null;
        }
        LayoutParams layoutParams = (LayoutParams) e2.getLayoutParams();
        int i9 = layoutParams.a;
        int i10 = layoutParams.a + layoutParams.b;
        if (super.a(view, i, oVar, sVar) == null) {
            return null;
        }
        if ((f(i) == 1) != this.k) {
            i4 = v() - 1;
            i3 = -1;
            i2 = -1;
        } else {
            i3 = v();
            i4 = 0;
            i2 = 1;
        }
        boolean z2 = this.i == 1 && h();
        int a2 = a(oVar2, sVar2, i4);
        View view6 = null;
        int i11 = -1;
        int i12 = 0;
        int i13 = 0;
        int i14 = -1;
        while (true) {
            if (i4 == i3) {
                view2 = view6;
                break;
            }
            int a3 = a(oVar2, sVar2, i4);
            View i15 = i(i4);
            if (i15 == e2) {
                view2 = view6;
                break;
            }
            if (!i15.hasFocusable() || a3 == a2) {
                LayoutParams layoutParams2 = (LayoutParams) i15.getLayoutParams();
                int i16 = layoutParams2.a;
                view3 = e2;
                i6 = i3;
                int i17 = layoutParams2.a + layoutParams2.b;
                if (i15.hasFocusable() && i16 == i9 && i17 == i10) {
                    return i15;
                }
                if ((!i15.hasFocusable() || view5 != null) && (i15.hasFocusable() || view6 != null)) {
                    int min = Math.min(i17, i10) - Math.max(i16, i9);
                    if (i15.hasFocusable()) {
                        if (min > i12) {
                            i5 = i11;
                            view4 = view6;
                            i7 = i13;
                            i8 = i14;
                            z = true;
                        } else {
                            if (min == i12) {
                                if (z2 == (i16 > i11)) {
                                    i5 = i11;
                                    view4 = view6;
                                    i7 = i13;
                                    i8 = i14;
                                    z = true;
                                }
                            }
                            i5 = i11;
                            view4 = view6;
                            i7 = i13;
                            i8 = i14;
                        }
                    } else if (view5 == null) {
                        i5 = i11;
                        view4 = view6;
                        boolean z3 = false;
                        if (a(i15, false, true)) {
                            i7 = i13;
                            if (min > i7) {
                                i8 = i14;
                                z = true;
                            } else if (min == i7) {
                                i8 = i14;
                                if (i16 > i8) {
                                    z3 = true;
                                }
                                if (z2 == z3) {
                                    z = true;
                                }
                            } else {
                                i8 = i14;
                            }
                        } else {
                            i7 = i13;
                            i8 = i14;
                        }
                    } else {
                        i5 = i11;
                        view4 = view6;
                        i7 = i13;
                        i8 = i14;
                    }
                    z = false;
                } else {
                    i5 = i11;
                    view4 = view6;
                    i7 = i13;
                    i8 = i14;
                    z = true;
                }
                if (z) {
                    if (i15.hasFocusable()) {
                        int i18 = layoutParams2.a;
                        view6 = view4;
                        i12 = Math.min(i17, i10) - Math.max(i16, i9);
                        i5 = i18;
                        i14 = i8;
                        view5 = i15;
                    } else {
                        view6 = i15;
                        i14 = layoutParams2.a;
                        i7 = Math.min(i17, i10) - Math.max(i16, i9);
                    }
                    i4 += i2;
                    i13 = i7;
                    e2 = view3;
                    i3 = i6;
                    i11 = i5;
                    oVar2 = oVar;
                    sVar2 = sVar;
                }
            } else if (view5 != null) {
                view2 = view6;
                break;
            } else {
                view3 = e2;
                i5 = i11;
                view4 = view6;
                i6 = i3;
                i7 = i13;
                i8 = i14;
            }
            view6 = view4;
            i14 = i8;
            i4 += i2;
            i13 = i7;
            e2 = view3;
            i3 = i6;
            i11 = i5;
            oVar2 = oVar;
            sVar2 = sVar;
        }
        return view5 != null ? view5 : view2;
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.i
    public boolean b() {
        return this.n == null && !this.a;
    }

    public static final class a extends b {
        @Override // androidx.recyclerview.widget.GridLayoutManager.b
        public int a(int i) {
            return 1;
        }

        @Override // androidx.recyclerview.widget.GridLayoutManager.b
        public int a(int i, int i2) {
            return i % i2;
        }
    }

    public static class LayoutParams extends RecyclerView.LayoutParams {
        int a = -1;
        int b = 0;

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

        public int a() {
            return this.a;
        }

        public int b() {
            return this.b;
        }
    }
}
