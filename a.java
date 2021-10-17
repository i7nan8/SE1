package androidx.recyclerview.widget;

import androidx.core.e.e;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.h;
import java.util.ArrayList;
import java.util.List;

class a implements h.a {
    final ArrayList<b> a;
    final ArrayList<b> b;
    final AbstractC٠٠٤٩a c;
    Runnable d;
    final boolean e;
    final h f;
    private e.a<b> g;
    private int h;

    /* access modifiers changed from: package-private */
    /* renamed from: androidx.recyclerview.widget.a$a  reason: collision with other inner class name */
    public interface AbstractC٠٠٤٩a {
        RecyclerView.v a(int i);

        void a(int i, int i2);

        void a(int i, int i2, Object obj);

        void a(b bVar);

        void b(int i, int i2);

        void b(b bVar);

        void c(int i, int i2);

        void d(int i, int i2);
    }

    a(AbstractC٠٠٤٩a aVar) {
        this(aVar, false);
    }

    a(AbstractC٠٠٤٩a aVar, boolean z) {
        this.g = new e.b(30);
        this.a = new ArrayList<>();
        this.b = new ArrayList<>();
        this.h = 0;
        this.c = aVar;
        this.e = z;
        this.f = new h(this);
    }

    /* access modifiers changed from: package-private */
    public void a() {
        a(this.a);
        a(this.b);
        this.h = 0;
    }

    /* access modifiers changed from: package-private */
    public void b() {
        this.f.a(this.a);
        int size = this.a.size();
        for (int i = 0; i < size; i++) {
            b bVar = this.a.get(i);
            int i2 = bVar.a;
            if (i2 == 4) {
                d(bVar);
            } else if (i2 != 8) {
                switch (i2) {
                    case 1:
                        f(bVar);
                        break;
                    case 2:
                        c(bVar);
                        break;
                }
            } else {
                b(bVar);
            }
            Runnable runnable = this.d;
            if (runnable != null) {
                runnable.run();
            }
        }
        this.a.clear();
    }

    /* access modifiers changed from: package-private */
    public void c() {
        int size = this.b.size();
        for (int i = 0; i < size; i++) {
            this.c.b(this.b.get(i));
        }
        a(this.b);
        this.h = 0;
    }

    private void b(b bVar) {
        g(bVar);
    }

    private void c(b bVar) {
        char c2;
        boolean z;
        int i = bVar.b;
        int i2 = bVar.b + bVar.d;
        int i3 = bVar.b;
        int i4 = 0;
        char c3 = 65535;
        while (i3 < i2) {
            if (this.c.a(i3) != null || d(i3)) {
                if (c3 == 0) {
                    e(a(2, i, i4, null));
                    z = true;
                } else {
                    z = false;
                }
                c2 = 1;
            } else {
                if (c3 == 1) {
                    g(a(2, i, i4, null));
                    z = true;
                } else {
                    z = false;
                }
                c2 = 0;
            }
            if (z) {
                i3 -= i4;
                i2 -= i4;
                i4 = 1;
            } else {
                i4++;
            }
            i3++;
            c3 = c2;
        }
        if (i4 != bVar.d) {
            a(bVar);
            bVar = a(2, i, i4, null);
        }
        if (c3 == 0) {
            e(bVar);
        } else {
            g(bVar);
        }
    }

    private void d(b bVar) {
        int i = bVar.b;
        int i2 = bVar.b + bVar.d;
        int i3 = i;
        int i4 = 0;
        char c2 = 65535;
        for (int i5 = bVar.b; i5 < i2; i5++) {
            if (this.c.a(i5) != null || d(i5)) {
                if (c2 == 0) {
                    e(a(4, i3, i4, bVar.c));
                    i3 = i5;
                    i4 = 0;
                }
                c2 = 1;
            } else {
                if (c2 == 1) {
                    g(a(4, i3, i4, bVar.c));
                    i3 = i5;
                    i4 = 0;
                }
                c2 = 0;
            }
            i4++;
        }
        if (i4 != bVar.d) {
            Object obj = bVar.c;
            a(bVar);
            bVar = a(4, i3, i4, obj);
        }
        if (c2 == 0) {
            e(bVar);
        } else {
            g(bVar);
        }
    }

    private void e(b bVar) {
        int i;
        if (bVar.a == 1 || bVar.a == 8) {
            throw new IllegalArgumentException("should not dispatch add or move for pre layout");
        }
        int d2 = d(bVar.b, bVar.a);
        int i2 = bVar.b;
        int i3 = bVar.a;
        if (i3 == 2) {
            i = 0;
        } else if (i3 == 4) {
            i = 1;
        } else {
            throw new IllegalArgumentException("op should be remove or update." + bVar);
        }
        int i4 = d2;
        int i5 = i2;
        int i6 = 1;
        for (int i7 = 1; i7 < bVar.d; i7++) {
            int d3 = d(bVar.b + (i * i7), bVar.a);
            int i8 = bVar.a;
            if (i8 != 2 ? i8 != 4 ? false : d3 == i4 + 1 : d3 == i4) {
                i6++;
            } else {
                b a2 = a(bVar.a, i4, i6, bVar.c);
                a(a2, i5);
                a(a2);
                if (bVar.a == 4) {
                    i5 += i6;
                }
                i4 = d3;
                i6 = 1;
            }
        }
        Object obj = bVar.c;
        a(bVar);
        if (i6 > 0) {
            b a3 = a(bVar.a, i4, i6, obj);
            a(a3, i5);
            a(a3);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(b bVar, int i) {
        this.c.a(bVar);
        int i2 = bVar.a;
        if (i2 == 2) {
            this.c.a(i, bVar.d);
        } else if (i2 == 4) {
            this.c.a(i, bVar.d, bVar.c);
        } else {
            throw new IllegalArgumentException("only remove and update ops can be dispatched in first pass");
        }
    }

    private int d(int i, int i2) {
        int i3;
        int i4;
        for (int size = this.b.size() - 1; size >= 0; size--) {
            b bVar = this.b.get(size);
            if (bVar.a == 8) {
                if (bVar.b < bVar.d) {
                    i4 = bVar.b;
                    i3 = bVar.d;
                } else {
                    i4 = bVar.d;
                    i3 = bVar.b;
                }
                if (i < i4 || i > i3) {
                    if (i < bVar.b) {
                        if (i2 == 1) {
                            bVar.b++;
                            bVar.d++;
                        } else if (i2 == 2) {
                            bVar.b--;
                            bVar.d--;
                        }
                    }
                } else if (i4 == bVar.b) {
                    if (i2 == 1) {
                        bVar.d++;
                    } else if (i2 == 2) {
                        bVar.d--;
                    }
                    i++;
                } else {
                    if (i2 == 1) {
                        bVar.b++;
                    } else if (i2 == 2) {
                        bVar.b--;
                    }
                    i--;
                }
            } else if (bVar.b <= i) {
                if (bVar.a == 1) {
                    i -= bVar.d;
                } else if (bVar.a == 2) {
                    i += bVar.d;
                }
            } else if (i2 == 1) {
                bVar.b++;
            } else if (i2 == 2) {
                bVar.b--;
            }
        }
        for (int size2 = this.b.size() - 1; size2 >= 0; size2--) {
            b bVar2 = this.b.get(size2);
            if (bVar2.a == 8) {
                if (bVar2.d == bVar2.b || bVar2.d < 0) {
                    this.b.remove(size2);
                    a(bVar2);
                }
            } else if (bVar2.d <= 0) {
                this.b.remove(size2);
                a(bVar2);
            }
        }
        return i;
    }

    private boolean d(int i) {
        int size = this.b.size();
        for (int i2 = 0; i2 < size; i2++) {
            b bVar = this.b.get(i2);
            if (bVar.a == 8) {
                if (a(bVar.d, i2 + 1) == i) {
                    return true;
                }
            } else if (bVar.a == 1) {
                int i3 = bVar.b + bVar.d;
                for (int i4 = bVar.b; i4 < i3; i4++) {
                    if (a(i4, i2 + 1) == i) {
                        return true;
                    }
                }
                continue;
            } else {
                continue;
            }
        }
        return false;
    }

    private void f(b bVar) {
        g(bVar);
    }

    private void g(b bVar) {
        this.b.add(bVar);
        int i = bVar.a;
        if (i == 4) {
            this.c.a(bVar.b, bVar.d, bVar.c);
        } else if (i != 8) {
            switch (i) {
                case 1:
                    this.c.c(bVar.b, bVar.d);
                    return;
                case 2:
                    this.c.b(bVar.b, bVar.d);
                    return;
                default:
                    throw new IllegalArgumentException("Unknown update op type for " + bVar);
            }
        } else {
            this.c.d(bVar.b, bVar.d);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean d() {
        return this.a.size() > 0;
    }

    /* access modifiers changed from: package-private */
    public boolean a(int i) {
        return (i & this.h) != 0;
    }

    /* access modifiers changed from: package-private */
    public int b(int i) {
        return a(i, 0);
    }

    /* access modifiers changed from: package-private */
    public int a(int i, int i2) {
        int size = this.b.size();
        while (i2 < size) {
            b bVar = this.b.get(i2);
            if (bVar.a == 8) {
                if (bVar.b == i) {
                    i = bVar.d;
                } else {
                    if (bVar.b < i) {
                        i--;
                    }
                    if (bVar.d <= i) {
                        i++;
                    }
                }
            } else if (bVar.b > i) {
                continue;
            } else if (bVar.a == 2) {
                if (i < bVar.b + bVar.d) {
                    return -1;
                }
                i -= bVar.d;
            } else if (bVar.a == 1) {
                i += bVar.d;
            }
            i2++;
        }
        return i;
    }

    /* access modifiers changed from: package-private */
    public boolean a(int i, int i2, Object obj) {
        if (i2 < 1) {
            return false;
        }
        this.a.add(a(4, i, i2, obj));
        this.h |= 4;
        if (this.a.size() == 1) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean b(int i, int i2) {
        if (i2 < 1) {
            return false;
        }
        this.a.add(a(1, i, i2, null));
        this.h |= 1;
        if (this.a.size() == 1) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean c(int i, int i2) {
        if (i2 < 1) {
            return false;
        }
        this.a.add(a(2, i, i2, null));
        this.h |= 2;
        if (this.a.size() == 1) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean a(int i, int i2, int i3) {
        if (i == i2) {
            return false;
        }
        if (i3 == 1) {
            this.a.add(a(8, i, i2, null));
            this.h |= 8;
            if (this.a.size() == 1) {
                return true;
            }
            return false;
        }
        throw new IllegalArgumentException("Moving more than 1 item is not supported yet");
    }

    /* access modifiers changed from: package-private */
    public void e() {
        c();
        int size = this.a.size();
        for (int i = 0; i < size; i++) {
            b bVar = this.a.get(i);
            int i2 = bVar.a;
            if (i2 == 4) {
                this.c.b(bVar);
                this.c.a(bVar.b, bVar.d, bVar.c);
            } else if (i2 != 8) {
                switch (i2) {
                    case 1:
                        this.c.b(bVar);
                        this.c.c(bVar.b, bVar.d);
                        break;
                    case 2:
                        this.c.b(bVar);
                        this.c.a(bVar.b, bVar.d);
                        break;
                }
            } else {
                this.c.b(bVar);
                this.c.d(bVar.b, bVar.d);
            }
            Runnable runnable = this.d;
            if (runnable != null) {
                runnable.run();
            }
        }
        a(this.a);
        this.h = 0;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0047, code lost:
        continue;
     */
    public int c(int i) {
        int size = this.a.size();
        for (int i2 = 0; i2 < size; i2++) {
            b bVar = this.a.get(i2);
            int i3 = bVar.a;
            if (i3 != 8) {
                switch (i3) {
                    case 1:
                        if (bVar.b <= i) {
                            i += bVar.d;
                            break;
                        } else {
                            continue;
                        }
                    case 2:
                        if (bVar.b > i) {
                            continue;
                        } else if (bVar.b + bVar.d <= i) {
                            i -= bVar.d;
                            break;
                        } else {
                            return -1;
                        }
                }
            } else if (bVar.b == i) {
                i = bVar.d;
            } else {
                if (bVar.b < i) {
                    i--;
                }
                if (bVar.d <= i) {
                    i++;
                }
            }
        }
        return i;
    }

    /* access modifiers changed from: package-private */
    public boolean f() {
        return !this.b.isEmpty() && !this.a.isEmpty();
    }

    /* access modifiers changed from: package-private */
    public static class b {
        int a;
        int b;
        Object c;
        int d;

        b(int i, int i2, int i3, Object obj) {
            this.a = i;
            this.b = i2;
            this.d = i3;
            this.c = obj;
        }

        /* access modifiers changed from: package-private */
        public String a() {
            int i = this.a;
            if (i == 4) {
                return "up";
            }
            if (i == 8) {
                return "mv";
            }
            switch (i) {
                case 1:
                    return "add";
                case 2:
                    return "rm";
                default:
                    return "??";
            }
        }

        public String toString() {
            return Integer.toHexString(System.identityHashCode(this)) + "[" + a() + ",s:" + this.b + "c:" + this.d + ",p:" + this.c + "]";
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            b bVar = (b) obj;
            int i = this.a;
            if (i != bVar.a) {
                return false;
            }
            if (i == 8 && Math.abs(this.d - this.b) == 1 && this.d == bVar.b && this.b == bVar.d) {
                return true;
            }
            if (this.d != bVar.d || this.b != bVar.b) {
                return false;
            }
            Object obj2 = this.c;
            if (obj2 != null) {
                if (!obj2.equals(bVar.c)) {
                    return false;
                }
            } else if (bVar.c != null) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return (((this.a * 31) + this.b) * 31) + this.d;
        }
    }

    @Override // androidx.recyclerview.widget.h.a
    public b a(int i, int i2, int i3, Object obj) {
        b a2 = this.g.a();
        if (a2 == null) {
            return new b(i, i2, i3, obj);
        }
        a2.a = i;
        a2.b = i2;
        a2.d = i3;
        a2.c = obj;
        return a2;
    }

    @Override // androidx.recyclerview.widget.h.a
    public void a(b bVar) {
        if (!this.e) {
            bVar.c = null;
            this.g.a(bVar);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(List<b> list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            a(list.get(i));
        }
        list.clear();
    }
}
