package androidx.recyclerview.widget;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/* access modifiers changed from: package-private */
public class k {
    static int a(RecyclerView.s sVar, i iVar, View view, View view2, RecyclerView.i iVar2, boolean z, boolean z2) {
        int i;
        if (iVar2.v() == 0 || sVar.e() == 0 || view == null || view2 == null) {
            return 0;
        }
        int min = Math.min(iVar2.d(view), iVar2.d(view2));
        int max = Math.max(iVar2.d(view), iVar2.d(view2));
        if (z2) {
            i = Math.max(0, (sVar.e() - max) - 1);
        } else {
            i = Math.max(0, min);
        }
        if (!z) {
            return i;
        }
        return Math.round((((float) i) * (((float) Math.abs(iVar.b(view2) - iVar.a(view))) / ((float) (Math.abs(iVar2.d(view) - iVar2.d(view2)) + 1)))) + ((float) (iVar.c() - iVar.a(view))));
    }

    static int a(RecyclerView.s sVar, i iVar, View view, View view2, RecyclerView.i iVar2, boolean z) {
        if (iVar2.v() == 0 || sVar.e() == 0 || view == null || view2 == null) {
            return 0;
        }
        if (!z) {
            return Math.abs(iVar2.d(view) - iVar2.d(view2)) + 1;
        }
        return Math.min(iVar.f(), iVar.b(view2) - iVar.a(view));
    }

    static int b(RecyclerView.s sVar, i iVar, View view, View view2, RecyclerView.i iVar2, boolean z) {
        if (iVar2.v() == 0 || sVar.e() == 0 || view == null || view2 == null) {
            return 0;
        }
        if (!z) {
            return sVar.e();
        }
        return (int) ((((float) (iVar.b(view2) - iVar.a(view))) / ((float) (Math.abs(iVar2.d(view) - iVar2.d(view2)) + 1))) * ((float) sVar.e()));
    }
}
