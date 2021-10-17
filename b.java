package androidx.recyclerview.widget;

import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

/* access modifiers changed from: package-private */
public class b {
    final AbstractC٠٠٥٠b a;
    final a b = new a();
    final List<View> c = new ArrayList();

    /* access modifiers changed from: package-private */
    /* renamed from: androidx.recyclerview.widget.b$b  reason: collision with other inner class name */
    public interface AbstractC٠٠٥٠b {
        int a();

        int a(View view);

        void a(int i);

        void a(View view, int i);

        void a(View view, int i, ViewGroup.LayoutParams layoutParams);

        View b(int i);

        RecyclerView.v b(View view);

        void b();

        void c(int i);

        void c(View view);

        void d(View view);
    }

    b(AbstractC٠٠٥٠b bVar) {
        this.a = bVar;
    }

    private void g(View view) {
        this.c.add(view);
        this.a.c(view);
    }

    private boolean h(View view) {
        if (!this.c.remove(view)) {
            return false;
        }
        this.a.d(view);
        return true;
    }

    /* access modifiers changed from: package-private */
    public void a(View view, boolean z) {
        a(view, -1, z);
    }

    /* access modifiers changed from: package-private */
    public void a(View view, int i, boolean z) {
        int i2;
        if (i < 0) {
            i2 = this.a.a();
        } else {
            i2 = f(i);
        }
        this.b.a(i2, z);
        if (z) {
            g(view);
        }
        this.a.a(view, i2);
    }

    private int f(int i) {
        if (i < 0) {
            return -1;
        }
        int a2 = this.a.a();
        int i2 = i;
        while (i2 < a2) {
            int e = i - (i2 - this.b.e(i2));
            if (e == 0) {
                while (this.b.c(i2)) {
                    i2++;
                }
                return i2;
            }
            i2 += e;
        }
        return -1;
    }

    /* access modifiers changed from: package-private */
    public void a(View view) {
        int a2 = this.a.a(view);
        if (a2 >= 0) {
            if (this.b.d(a2)) {
                h(view);
            }
            this.a.a(a2);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(int i) {
        int f = f(i);
        View b2 = this.a.b(f);
        if (b2 != null) {
            if (this.b.d(f)) {
                h(b2);
            }
            this.a.a(f);
        }
    }

    /* access modifiers changed from: package-private */
    public View b(int i) {
        return this.a.b(f(i));
    }

    /* access modifiers changed from: package-private */
    public void a() {
        this.b.a();
        for (int size = this.c.size() - 1; size >= 0; size--) {
            this.a.d(this.c.get(size));
            this.c.remove(size);
        }
        this.a.b();
    }

    /* access modifiers changed from: package-private */
    public View c(int i) {
        int size = this.c.size();
        for (int i2 = 0; i2 < size; i2++) {
            View view = this.c.get(i2);
            RecyclerView.v b2 = this.a.b(view);
            if (!(b2.getLayoutPosition() != i || b2.isInvalid() || b2.isRemoved())) {
                return view;
            }
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void a(View view, int i, ViewGroup.LayoutParams layoutParams, boolean z) {
        int i2;
        if (i < 0) {
            i2 = this.a.a();
        } else {
            i2 = f(i);
        }
        this.b.a(i2, z);
        if (z) {
            g(view);
        }
        this.a.a(view, i2, layoutParams);
    }

    /* access modifiers changed from: package-private */
    public int b() {
        return this.a.a() - this.c.size();
    }

    /* access modifiers changed from: package-private */
    public int c() {
        return this.a.a();
    }

    /* access modifiers changed from: package-private */
    public View d(int i) {
        return this.a.b(i);
    }

    /* access modifiers changed from: package-private */
    public void e(int i) {
        int f = f(i);
        this.b.d(f);
        this.a.c(f);
    }

    /* access modifiers changed from: package-private */
    public int b(View view) {
        int a2 = this.a.a(view);
        if (a2 != -1 && !this.b.c(a2)) {
            return a2 - this.b.e(a2);
        }
        return -1;
    }

    /* access modifiers changed from: package-private */
    public boolean c(View view) {
        return this.c.contains(view);
    }

    /* access modifiers changed from: package-private */
    public void d(View view) {
        int a2 = this.a.a(view);
        if (a2 >= 0) {
            this.b.a(a2);
            g(view);
            return;
        }
        throw new IllegalArgumentException("view is not a child, cannot hide " + view);
    }

    /* access modifiers changed from: package-private */
    public void e(View view) {
        int a2 = this.a.a(view);
        if (a2 < 0) {
            throw new IllegalArgumentException("view is not a child, cannot hide " + view);
        } else if (this.b.c(a2)) {
            this.b.b(a2);
            h(view);
        } else {
            throw new RuntimeException("trying to unhide a view that was not hidden" + view);
        }
    }

    public String toString() {
        return this.b.toString() + ", hidden list:" + this.c.size();
    }

    /* access modifiers changed from: package-private */
    public boolean f(View view) {
        int a2 = this.a.a(view);
        if (a2 == -1) {
            h(view);
            return true;
        } else if (!this.b.c(a2)) {
            return false;
        } else {
            this.b.d(a2);
            h(view);
            this.a.a(a2);
            return true;
        }
    }

    /* access modifiers changed from: package-private */
    public static class a {
        long a = 0;
        a b;

        a() {
        }

        /* access modifiers changed from: package-private */
        public void a(int i) {
            if (i >= 64) {
                b();
                this.b.a(i - 64);
                return;
            }
            this.a |= 1 << i;
        }

        private void b() {
            if (this.b == null) {
                this.b = new a();
            }
        }

        /* access modifiers changed from: package-private */
        public void b(int i) {
            if (i >= 64) {
                a aVar = this.b;
                if (aVar != null) {
                    aVar.b(i - 64);
                    return;
                }
                return;
            }
            this.a &= (1 << i) ^ -1;
        }

        /* access modifiers changed from: package-private */
        public boolean c(int i) {
            if (i < 64) {
                return (this.a & (1 << i)) != 0;
            }
            b();
            return this.b.c(i - 64);
        }

        /* access modifiers changed from: package-private */
        public void a() {
            this.a = 0;
            a aVar = this.b;
            if (aVar != null) {
                aVar.a();
            }
        }

        /* access modifiers changed from: package-private */
        public void a(int i, boolean z) {
            if (i >= 64) {
                b();
                this.b.a(i - 64, z);
                return;
            }
            boolean z2 = (this.a & Long.MIN_VALUE) != 0;
            long j = (1 << i) - 1;
            long j2 = this.a;
            this.a = ((j2 & (j ^ -1)) << 1) | (j2 & j);
            if (z) {
                a(i);
            } else {
                b(i);
            }
            if (z2 || this.b != null) {
                b();
                this.b.a(0, z2);
            }
        }

        /* access modifiers changed from: package-private */
        public boolean d(int i) {
            if (i >= 64) {
                b();
                return this.b.d(i - 64);
            }
            long j = 1 << i;
            boolean z = (this.a & j) != 0;
            this.a &= j ^ -1;
            long j2 = j - 1;
            long j3 = this.a;
            this.a = Long.rotateRight(j3 & (j2 ^ -1), 1) | (j3 & j2);
            a aVar = this.b;
            if (aVar != null) {
                if (aVar.c(0)) {
                    a(63);
                }
                this.b.d(0);
            }
            return z;
        }

        /* access modifiers changed from: package-private */
        public int e(int i) {
            a aVar = this.b;
            if (aVar == null) {
                if (i >= 64) {
                    return Long.bitCount(this.a);
                }
                return Long.bitCount(this.a & ((1 << i) - 1));
            } else if (i < 64) {
                return Long.bitCount(this.a & ((1 << i) - 1));
            } else {
                return aVar.e(i - 64) + Long.bitCount(this.a);
            }
        }

        public String toString() {
            if (this.b == null) {
                return Long.toBinaryString(this.a);
            }
            return this.b.toString() + "xx" + Long.toBinaryString(this.a);
        }
    }
}
