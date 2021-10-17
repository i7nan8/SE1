package androidx.recyclerview.widget;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

public abstract class l extends RecyclerView.f {
    boolean h = true;

    public abstract boolean a(RecyclerView.v vVar);

    public abstract boolean a(RecyclerView.v vVar, int i, int i2, int i3, int i4);

    public abstract boolean a(RecyclerView.v vVar, RecyclerView.v vVar2, int i, int i2, int i3, int i4);

    public abstract boolean b(RecyclerView.v vVar);

    public void c(RecyclerView.v vVar, boolean z) {
    }

    public void d(RecyclerView.v vVar, boolean z) {
    }

    public void o(RecyclerView.v vVar) {
    }

    public void p(RecyclerView.v vVar) {
    }

    public void q(RecyclerView.v vVar) {
    }

    public void r(RecyclerView.v vVar) {
    }

    public void s(RecyclerView.v vVar) {
    }

    public void t(RecyclerView.v vVar) {
    }

    @Override // androidx.recyclerview.widget.RecyclerView.f
    public boolean h(RecyclerView.v vVar) {
        return !this.h || vVar.isInvalid();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.f
    public boolean a(RecyclerView.v vVar, RecyclerView.f.c cVar, RecyclerView.f.c cVar2) {
        int i = cVar.a;
        int i2 = cVar.b;
        View view = vVar.itemView;
        int left = cVar2 == null ? view.getLeft() : cVar2.a;
        int top = cVar2 == null ? view.getTop() : cVar2.b;
        if (vVar.isRemoved() || (i == left && i2 == top)) {
            return a(vVar);
        }
        view.layout(left, top, view.getWidth() + left, view.getHeight() + top);
        return a(vVar, i, i2, left, top);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.f
    public boolean b(RecyclerView.v vVar, RecyclerView.f.c cVar, RecyclerView.f.c cVar2) {
        if (cVar == null || (cVar.a == cVar2.a && cVar.b == cVar2.b)) {
            return b(vVar);
        }
        return a(vVar, cVar.a, cVar.b, cVar2.a, cVar2.b);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.f
    public boolean c(RecyclerView.v vVar, RecyclerView.f.c cVar, RecyclerView.f.c cVar2) {
        if (cVar.a != cVar2.a || cVar.b != cVar2.b) {
            return a(vVar, cVar.a, cVar.b, cVar2.a, cVar2.b);
        }
        j(vVar);
        return false;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.f
    public boolean a(RecyclerView.v vVar, RecyclerView.v vVar2, RecyclerView.f.c cVar, RecyclerView.f.c cVar2) {
        int i;
        int i2;
        int i3 = cVar.a;
        int i4 = cVar.b;
        if (vVar2.shouldIgnore()) {
            int i5 = cVar.a;
            i = cVar.b;
            i2 = i5;
        } else {
            i2 = cVar2.a;
            i = cVar2.b;
        }
        return a(vVar, vVar2, i3, i4, i2, i);
    }

    public final void i(RecyclerView.v vVar) {
        p(vVar);
        f(vVar);
    }

    public final void j(RecyclerView.v vVar) {
        t(vVar);
        f(vVar);
    }

    public final void k(RecyclerView.v vVar) {
        r(vVar);
        f(vVar);
    }

    public final void a(RecyclerView.v vVar, boolean z) {
        d(vVar, z);
        f(vVar);
    }

    public final void l(RecyclerView.v vVar) {
        o(vVar);
    }

    public final void m(RecyclerView.v vVar) {
        s(vVar);
    }

    public final void n(RecyclerView.v vVar) {
        q(vVar);
    }

    public final void b(RecyclerView.v vVar, boolean z) {
        c(vVar, z);
    }
}
