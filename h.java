package androidx.recyclerview.widget;

import androidx.recyclerview.widget.a;
import java.util.List;

/* access modifiers changed from: package-private */
public class h {
    final a a;

    /* access modifiers changed from: package-private */
    public interface a {
        a.b a(int i, int i2, int i3, Object obj);

        void a(a.b bVar);
    }

    h(a aVar) {
        this.a = aVar;
    }

    /* access modifiers changed from: package-private */
    public void a(List<a.b> list) {
        while (true) {
            int b = b(list);
            if (b != -1) {
                a(list, b, b + 1);
            } else {
                return;
            }
        }
    }

    private void a(List<a.b> list, int i, int i2) {
        a.b bVar = list.get(i);
        a.b bVar2 = list.get(i2);
        int i3 = bVar2.a;
        if (i3 != 4) {
            switch (i3) {
                case 1:
                    c(list, i, bVar, i2, bVar2);
                    return;
                case 2:
                    a(list, i, bVar, i2, bVar2);
                    return;
                default:
                    return;
            }
        } else {
            b(list, i, bVar, i2, bVar2);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(List<a.b> list, int i, a.b bVar, int i2, a.b bVar2) {
        boolean z;
        boolean z2 = false;
        if (bVar.b < bVar.d) {
            if (bVar2.b == bVar.b && bVar2.d == bVar.d - bVar.b) {
                z = false;
                z2 = true;
            } else {
                z = false;
            }
        } else if (bVar2.b == bVar.d + 1 && bVar2.d == bVar.b - bVar.d) {
            z = true;
            z2 = true;
        } else {
            z = true;
        }
        if (bVar.d < bVar2.b) {
            bVar2.b--;
        } else if (bVar.d < bVar2.b + bVar2.d) {
            bVar2.d--;
            bVar.a = 2;
            bVar.d = 1;
            if (bVar2.d == 0) {
                list.remove(i2);
                this.a.a(bVar2);
                return;
            }
            return;
        }
        a.b bVar3 = null;
        if (bVar.b <= bVar2.b) {
            bVar2.b++;
        } else if (bVar.b < bVar2.b + bVar2.d) {
            bVar3 = this.a.a(2, bVar.b + 1, (bVar2.b + bVar2.d) - bVar.b, null);
            bVar2.d = bVar.b - bVar2.b;
        }
        if (z2) {
            list.set(i, bVar2);
            list.remove(i2);
            this.a.a(bVar);
            return;
        }
        if (z) {
            if (bVar3 != null) {
                if (bVar.b > bVar3.b) {
                    bVar.b -= bVar3.d;
                }
                if (bVar.d > bVar3.b) {
                    bVar.d -= bVar3.d;
                }
            }
            if (bVar.b > bVar2.b) {
                bVar.b -= bVar2.d;
            }
            if (bVar.d > bVar2.b) {
                bVar.d -= bVar2.d;
            }
        } else {
            if (bVar3 != null) {
                if (bVar.b >= bVar3.b) {
                    bVar.b -= bVar3.d;
                }
                if (bVar.d >= bVar3.b) {
                    bVar.d -= bVar3.d;
                }
            }
            if (bVar.b >= bVar2.b) {
                bVar.b -= bVar2.d;
            }
            if (bVar.d >= bVar2.b) {
                bVar.d -= bVar2.d;
            }
        }
        list.set(i, bVar2);
        if (bVar.b != bVar.d) {
            list.set(i2, bVar);
        } else {
            list.remove(i2);
        }
        if (bVar3 != null) {
            list.add(i, bVar3);
        }
    }

    private void c(List<a.b> list, int i, a.b bVar, int i2, a.b bVar2) {
        int i3 = bVar.d < bVar2.b ? -1 : 0;
        if (bVar.b < bVar2.b) {
            i3++;
        }
        if (bVar2.b <= bVar.b) {
            bVar.b += bVar2.d;
        }
        if (bVar2.b <= bVar.d) {
            bVar.d += bVar2.d;
        }
        bVar2.b += i3;
        list.set(i, bVar2);
        list.set(i2, bVar);
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0035  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x005d  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0061  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x006b  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0070  */
    /* JADX WARNING: Removed duplicated region for block: B:22:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x002f  */
    public void b(List<a.b> list, int i, a.b bVar, int i2, a.b bVar2) {
        a.b bVar3;
        a.b bVar4 = null;
        if (bVar.d < bVar2.b) {
            bVar2.b--;
        } else if (bVar.d < bVar2.b + bVar2.d) {
            bVar2.d--;
            bVar3 = this.a.a(4, bVar.b, 1, bVar2.c);
            if (bVar.b > bVar2.b) {
                bVar2.b++;
            } else if (bVar.b < bVar2.b + bVar2.d) {
                int i3 = (bVar2.b + bVar2.d) - bVar.b;
                bVar4 = this.a.a(4, bVar.b + 1, i3, bVar2.c);
                bVar2.d -= i3;
            }
            list.set(i2, bVar);
            if (bVar2.d <= 0) {
                list.set(i, bVar2);
            } else {
                list.remove(i);
                this.a.a(bVar2);
            }
            if (bVar3 != null) {
                list.add(i, bVar3);
            }
            if (bVar4 == null) {
                list.add(i, bVar4);
                return;
            }
            return;
        }
        bVar3 = null;
        if (bVar.b > bVar2.b) {
        }
        list.set(i2, bVar);
        if (bVar2.d <= 0) {
        }
        if (bVar3 != null) {
        }
        if (bVar4 == null) {
        }
    }

    private int b(List<a.b> list) {
        boolean z = false;
        for (int size = list.size() - 1; size >= 0; size--) {
            if (list.get(size).a != 8) {
                z = true;
            } else if (z) {
                return size;
            }
        }
        return -1;
    }
}
