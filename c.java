package androidx.recyclerview.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewPropertyAnimator;
import androidx.core.f.r;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.azeckoski.reflectutils.transcoders.JSONTranscoder;

public class c extends l {
    private static TimeInterpolator i;
    ArrayList<ArrayList<RecyclerView.v>> a = new ArrayList<>();
    ArrayList<ArrayList<b>> b = new ArrayList<>();
    ArrayList<ArrayList<a>> c = new ArrayList<>();
    ArrayList<RecyclerView.v> d = new ArrayList<>();
    ArrayList<RecyclerView.v> e = new ArrayList<>();
    ArrayList<RecyclerView.v> f = new ArrayList<>();
    ArrayList<RecyclerView.v> g = new ArrayList<>();
    private ArrayList<RecyclerView.v> j = new ArrayList<>();
    private ArrayList<RecyclerView.v> k = new ArrayList<>();
    private ArrayList<b> l = new ArrayList<>();
    private ArrayList<a> m = new ArrayList<>();

    /* access modifiers changed from: private */
    public static class b {
        public RecyclerView.v a;
        public int b;
        public int c;
        public int d;
        public int e;

        b(RecyclerView.v vVar, int i, int i2, int i3, int i4) {
            this.a = vVar;
            this.b = i;
            this.c = i2;
            this.d = i3;
            this.e = i4;
        }
    }

    /* access modifiers changed from: private */
    public static class a {
        public RecyclerView.v a;
        public RecyclerView.v b;
        public int c;
        public int d;
        public int e;
        public int f;

        private a(RecyclerView.v vVar, RecyclerView.v vVar2) {
            this.a = vVar;
            this.b = vVar2;
        }

        a(RecyclerView.v vVar, RecyclerView.v vVar2, int i, int i2, int i3, int i4) {
            this(vVar, vVar2);
            this.c = i;
            this.d = i2;
            this.e = i3;
            this.f = i4;
        }

        public String toString() {
            return "ChangeInfo{oldHolder=" + this.a + ", newHolder=" + this.b + ", fromX=" + this.c + ", fromY=" + this.d + ", toX=" + this.e + ", toY=" + this.f + JSONTranscoder.OBJ_END;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.f
    public void a() {
        boolean z = !this.j.isEmpty();
        boolean z2 = !this.l.isEmpty();
        boolean z3 = !this.m.isEmpty();
        boolean z4 = !this.k.isEmpty();
        if (z || z2 || z4 || z3) {
            Iterator<RecyclerView.v> it = this.j.iterator();
            while (it.hasNext()) {
                u(it.next());
            }
            this.j.clear();
            if (z2) {
                final ArrayList<b> arrayList = new ArrayList<>();
                arrayList.addAll(this.l);
                this.b.add(arrayList);
                this.l.clear();
                AnonymousClass1 r6 = new Runnable() {
                    /* class androidx.recyclerview.widget.c.AnonymousClass1 */

                    public void run() {
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            b bVar = (b) it.next();
                            c.this.b(bVar.a, bVar.b, bVar.c, bVar.d, bVar.e);
                        }
                        arrayList.clear();
                        c.this.b.remove(arrayList);
                    }
                };
                if (z) {
                    r.a(arrayList.get(0).a.itemView, r6, g());
                } else {
                    r6.run();
                }
            }
            if (z3) {
                final ArrayList<a> arrayList2 = new ArrayList<>();
                arrayList2.addAll(this.m);
                this.c.add(arrayList2);
                this.m.clear();
                AnonymousClass2 r62 = new Runnable() {
                    /* class androidx.recyclerview.widget.c.AnonymousClass2 */

                    public void run() {
                        Iterator it = arrayList2.iterator();
                        while (it.hasNext()) {
                            c.this.a((a) it.next());
                        }
                        arrayList2.clear();
                        c.this.c.remove(arrayList2);
                    }
                };
                if (z) {
                    r.a(arrayList2.get(0).a.itemView, r62, g());
                } else {
                    r62.run();
                }
            }
            if (z4) {
                final ArrayList<RecyclerView.v> arrayList3 = new ArrayList<>();
                arrayList3.addAll(this.k);
                this.a.add(arrayList3);
                this.k.clear();
                AnonymousClass3 r5 = new Runnable() {
                    /* class androidx.recyclerview.widget.c.AnonymousClass3 */

                    public void run() {
                        Iterator it = arrayList3.iterator();
                        while (it.hasNext()) {
                            c.this.c((RecyclerView.v) it.next());
                        }
                        arrayList3.clear();
                        c.this.a.remove(arrayList3);
                    }
                };
                if (z || z2 || z3) {
                    long j2 = 0;
                    long g2 = z ? g() : 0;
                    long e2 = z2 ? e() : 0;
                    if (z3) {
                        j2 = h();
                    }
                    r.a(arrayList3.get(0).itemView, r5, g2 + Math.max(e2, j2));
                    return;
                }
                r5.run();
            }
        }
    }

    @Override // androidx.recyclerview.widget.l
    public boolean a(RecyclerView.v vVar) {
        v(vVar);
        this.j.add(vVar);
        return true;
    }

    private void u(final RecyclerView.v vVar) {
        final View view = vVar.itemView;
        final ViewPropertyAnimator animate = view.animate();
        this.f.add(vVar);
        animate.setDuration(g()).alpha(0.0f).setListener(new AnimatorListenerAdapter() {
            /* class androidx.recyclerview.widget.c.AnonymousClass4 */

            public void onAnimationStart(Animator animator) {
                c.this.l(vVar);
            }

            public void onAnimationEnd(Animator animator) {
                animate.setListener(null);
                view.setAlpha(1.0f);
                c.this.i(vVar);
                c.this.f.remove(vVar);
                c.this.c();
            }
        }).start();
    }

    @Override // androidx.recyclerview.widget.l
    public boolean b(RecyclerView.v vVar) {
        v(vVar);
        vVar.itemView.setAlpha(0.0f);
        this.k.add(vVar);
        return true;
    }

    /* access modifiers changed from: package-private */
    public void c(final RecyclerView.v vVar) {
        final View view = vVar.itemView;
        final ViewPropertyAnimator animate = view.animate();
        this.d.add(vVar);
        animate.alpha(1.0f).setDuration(f()).setListener(new AnimatorListenerAdapter() {
            /* class androidx.recyclerview.widget.c.AnonymousClass5 */

            public void onAnimationStart(Animator animator) {
                c.this.n(vVar);
            }

            public void onAnimationCancel(Animator animator) {
                view.setAlpha(1.0f);
            }

            public void onAnimationEnd(Animator animator) {
                animate.setListener(null);
                c.this.k(vVar);
                c.this.d.remove(vVar);
                c.this.c();
            }
        }).start();
    }

    @Override // androidx.recyclerview.widget.l
    public boolean a(RecyclerView.v vVar, int i2, int i3, int i4, int i5) {
        View view = vVar.itemView;
        int translationX = i2 + ((int) vVar.itemView.getTranslationX());
        int translationY = i3 + ((int) vVar.itemView.getTranslationY());
        v(vVar);
        int i6 = i4 - translationX;
        int i7 = i5 - translationY;
        if (i6 == 0 && i7 == 0) {
            j(vVar);
            return false;
        }
        if (i6 != 0) {
            view.setTranslationX((float) (-i6));
        }
        if (i7 != 0) {
            view.setTranslationY((float) (-i7));
        }
        this.l.add(new b(vVar, translationX, translationY, i4, i5));
        return true;
    }

    /* access modifiers changed from: package-private */
    public void b(final RecyclerView.v vVar, int i2, int i3, int i4, int i5) {
        final View view = vVar.itemView;
        final int i6 = i4 - i2;
        final int i7 = i5 - i3;
        if (i6 != 0) {
            view.animate().translationX(0.0f);
        }
        if (i7 != 0) {
            view.animate().translationY(0.0f);
        }
        final ViewPropertyAnimator animate = view.animate();
        this.e.add(vVar);
        animate.setDuration(e()).setListener(new AnimatorListenerAdapter() {
            /* class androidx.recyclerview.widget.c.AnonymousClass6 */

            public void onAnimationStart(Animator animator) {
                c.this.m(vVar);
            }

            public void onAnimationCancel(Animator animator) {
                if (i6 != 0) {
                    view.setTranslationX(0.0f);
                }
                if (i7 != 0) {
                    view.setTranslationY(0.0f);
                }
            }

            public void onAnimationEnd(Animator animator) {
                animate.setListener(null);
                c.this.j(vVar);
                c.this.e.remove(vVar);
                c.this.c();
            }
        }).start();
    }

    @Override // androidx.recyclerview.widget.l
    public boolean a(RecyclerView.v vVar, RecyclerView.v vVar2, int i2, int i3, int i4, int i5) {
        if (vVar == vVar2) {
            return a(vVar, i2, i3, i4, i5);
        }
        float translationX = vVar.itemView.getTranslationX();
        float translationY = vVar.itemView.getTranslationY();
        float alpha = vVar.itemView.getAlpha();
        v(vVar);
        int i6 = (int) (((float) (i4 - i2)) - translationX);
        int i7 = (int) (((float) (i5 - i3)) - translationY);
        vVar.itemView.setTranslationX(translationX);
        vVar.itemView.setTranslationY(translationY);
        vVar.itemView.setAlpha(alpha);
        if (vVar2 != null) {
            v(vVar2);
            vVar2.itemView.setTranslationX((float) (-i6));
            vVar2.itemView.setTranslationY((float) (-i7));
            vVar2.itemView.setAlpha(0.0f);
        }
        this.m.add(new a(vVar, vVar2, i2, i3, i4, i5));
        return true;
    }

    /* access modifiers changed from: package-private */
    public void a(final a aVar) {
        final View view;
        RecyclerView.v vVar = aVar.a;
        final View view2 = null;
        if (vVar == null) {
            view = null;
        } else {
            view = vVar.itemView;
        }
        RecyclerView.v vVar2 = aVar.b;
        if (vVar2 != null) {
            view2 = vVar2.itemView;
        }
        if (view != null) {
            final ViewPropertyAnimator duration = view.animate().setDuration(h());
            this.g.add(aVar.a);
            duration.translationX((float) (aVar.e - aVar.c));
            duration.translationY((float) (aVar.f - aVar.d));
            duration.alpha(0.0f).setListener(new AnimatorListenerAdapter() {
                /* class androidx.recyclerview.widget.c.AnonymousClass7 */

                public void onAnimationStart(Animator animator) {
                    c.this.b(aVar.a, true);
                }

                public void onAnimationEnd(Animator animator) {
                    duration.setListener(null);
                    view.setAlpha(1.0f);
                    view.setTranslationX(0.0f);
                    view.setTranslationY(0.0f);
                    c.this.a(aVar.a, true);
                    c.this.g.remove(aVar.a);
                    c.this.c();
                }
            }).start();
        }
        if (view2 != null) {
            final ViewPropertyAnimator animate = view2.animate();
            this.g.add(aVar.b);
            animate.translationX(0.0f).translationY(0.0f).setDuration(h()).alpha(1.0f).setListener(new AnimatorListenerAdapter() {
                /* class androidx.recyclerview.widget.c.AnonymousClass8 */

                public void onAnimationStart(Animator animator) {
                    c.this.b(aVar.b, false);
                }

                public void onAnimationEnd(Animator animator) {
                    animate.setListener(null);
                    view2.setAlpha(1.0f);
                    view2.setTranslationX(0.0f);
                    view2.setTranslationY(0.0f);
                    c.this.a(aVar.b, false);
                    c.this.g.remove(aVar.b);
                    c.this.c();
                }
            }).start();
        }
    }

    private void a(List<a> list, RecyclerView.v vVar) {
        for (int size = list.size() - 1; size >= 0; size--) {
            a aVar = list.get(size);
            if (a(aVar, vVar) && aVar.a == null && aVar.b == null) {
                list.remove(aVar);
            }
        }
    }

    private void b(a aVar) {
        if (aVar.a != null) {
            a(aVar, aVar.a);
        }
        if (aVar.b != null) {
            a(aVar, aVar.b);
        }
    }

    private boolean a(a aVar, RecyclerView.v vVar) {
        boolean z = false;
        if (aVar.b == vVar) {
            aVar.b = null;
        } else if (aVar.a != vVar) {
            return false;
        } else {
            aVar.a = null;
            z = true;
        }
        vVar.itemView.setAlpha(1.0f);
        vVar.itemView.setTranslationX(0.0f);
        vVar.itemView.setTranslationY(0.0f);
        a(vVar, z);
        return true;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.f
    public void d(RecyclerView.v vVar) {
        View view = vVar.itemView;
        view.animate().cancel();
        int size = this.l.size();
        while (true) {
            size--;
            if (size < 0) {
                break;
            } else if (this.l.get(size).a == vVar) {
                view.setTranslationY(0.0f);
                view.setTranslationX(0.0f);
                j(vVar);
                this.l.remove(size);
            }
        }
        a(this.m, vVar);
        if (this.j.remove(vVar)) {
            view.setAlpha(1.0f);
            i(vVar);
        }
        if (this.k.remove(vVar)) {
            view.setAlpha(1.0f);
            k(vVar);
        }
        for (int size2 = this.c.size() - 1; size2 >= 0; size2--) {
            ArrayList<a> arrayList = this.c.get(size2);
            a(arrayList, vVar);
            if (arrayList.isEmpty()) {
                this.c.remove(size2);
            }
        }
        for (int size3 = this.b.size() - 1; size3 >= 0; size3--) {
            ArrayList<b> arrayList2 = this.b.get(size3);
            int size4 = arrayList2.size() - 1;
            while (true) {
                if (size4 < 0) {
                    break;
                } else if (arrayList2.get(size4).a == vVar) {
                    view.setTranslationY(0.0f);
                    view.setTranslationX(0.0f);
                    j(vVar);
                    arrayList2.remove(size4);
                    if (arrayList2.isEmpty()) {
                        this.b.remove(size3);
                    }
                } else {
                    size4--;
                }
            }
        }
        for (int size5 = this.a.size() - 1; size5 >= 0; size5--) {
            ArrayList<RecyclerView.v> arrayList3 = this.a.get(size5);
            if (arrayList3.remove(vVar)) {
                view.setAlpha(1.0f);
                k(vVar);
                if (arrayList3.isEmpty()) {
                    this.a.remove(size5);
                }
            }
        }
        this.f.remove(vVar);
        this.d.remove(vVar);
        this.g.remove(vVar);
        this.e.remove(vVar);
        c();
    }

    private void v(RecyclerView.v vVar) {
        if (i == null) {
            i = new ValueAnimator().getInterpolator();
        }
        vVar.itemView.animate().setInterpolator(i);
        d(vVar);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.f
    public boolean b() {
        return !this.k.isEmpty() || !this.m.isEmpty() || !this.l.isEmpty() || !this.j.isEmpty() || !this.e.isEmpty() || !this.f.isEmpty() || !this.d.isEmpty() || !this.g.isEmpty() || !this.b.isEmpty() || !this.a.isEmpty() || !this.c.isEmpty();
    }

    /* access modifiers changed from: package-private */
    public void c() {
        if (!b()) {
            i();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.f
    public void d() {
        int size = this.l.size();
        while (true) {
            size--;
            if (size < 0) {
                break;
            }
            b bVar = this.l.get(size);
            View view = bVar.a.itemView;
            view.setTranslationY(0.0f);
            view.setTranslationX(0.0f);
            j(bVar.a);
            this.l.remove(size);
        }
        for (int size2 = this.j.size() - 1; size2 >= 0; size2--) {
            i(this.j.get(size2));
            this.j.remove(size2);
        }
        int size3 = this.k.size();
        while (true) {
            size3--;
            if (size3 < 0) {
                break;
            }
            RecyclerView.v vVar = this.k.get(size3);
            vVar.itemView.setAlpha(1.0f);
            k(vVar);
            this.k.remove(size3);
        }
        for (int size4 = this.m.size() - 1; size4 >= 0; size4--) {
            b(this.m.get(size4));
        }
        this.m.clear();
        if (b()) {
            for (int size5 = this.b.size() - 1; size5 >= 0; size5--) {
                ArrayList<b> arrayList = this.b.get(size5);
                for (int size6 = arrayList.size() - 1; size6 >= 0; size6--) {
                    b bVar2 = arrayList.get(size6);
                    View view2 = bVar2.a.itemView;
                    view2.setTranslationY(0.0f);
                    view2.setTranslationX(0.0f);
                    j(bVar2.a);
                    arrayList.remove(size6);
                    if (arrayList.isEmpty()) {
                        this.b.remove(arrayList);
                    }
                }
            }
            for (int size7 = this.a.size() - 1; size7 >= 0; size7--) {
                ArrayList<RecyclerView.v> arrayList2 = this.a.get(size7);
                for (int size8 = arrayList2.size() - 1; size8 >= 0; size8--) {
                    RecyclerView.v vVar2 = arrayList2.get(size8);
                    vVar2.itemView.setAlpha(1.0f);
                    k(vVar2);
                    arrayList2.remove(size8);
                    if (arrayList2.isEmpty()) {
                        this.a.remove(arrayList2);
                    }
                }
            }
            for (int size9 = this.c.size() - 1; size9 >= 0; size9--) {
                ArrayList<a> arrayList3 = this.c.get(size9);
                for (int size10 = arrayList3.size() - 1; size10 >= 0; size10--) {
                    b(arrayList3.get(size10));
                    if (arrayList3.isEmpty()) {
                        this.c.remove(arrayList3);
                    }
                }
            }
            a(this.f);
            a(this.e);
            a(this.d);
            a(this.g);
            i();
        }
    }

    /* access modifiers changed from: package-private */
    public void a(List<RecyclerView.v> list) {
        for (int size = list.size() - 1; size >= 0; size--) {
            list.get(size).itemView.animate().cancel();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.f
    public boolean a(RecyclerView.v vVar, List<Object> list) {
        return !list.isEmpty() || super.a(vVar, list);
    }
}
