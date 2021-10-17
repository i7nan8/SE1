package androidx.recyclerview.widget;

import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import androidx.core.f.a.c;

public class j extends androidx.core.f.a {
    final RecyclerView a;
    final androidx.core.f.a b = new a(this);

    public j(RecyclerView recyclerView) {
        this.a = recyclerView;
    }

    /* access modifiers changed from: package-private */
    public boolean a() {
        return this.a.hasPendingAdapterUpdates();
    }

    @Override // androidx.core.f.a
    public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
        if (super.performAccessibilityAction(view, i, bundle)) {
            return true;
        }
        if (a() || this.a.getLayoutManager() == null) {
            return false;
        }
        return this.a.getLayoutManager().a(i, bundle);
    }

    @Override // androidx.core.f.a
    public void onInitializeAccessibilityNodeInfo(View view, c cVar) {
        super.onInitializeAccessibilityNodeInfo(view, cVar);
        cVar.b((CharSequence) RecyclerView.class.getName());
        if (!a() && this.a.getLayoutManager() != null) {
            this.a.getLayoutManager().a(cVar);
        }
    }

    @Override // androidx.core.f.a
    public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(view, accessibilityEvent);
        accessibilityEvent.setClassName(RecyclerView.class.getName());
        if ((view instanceof RecyclerView) && !a()) {
            RecyclerView recyclerView = (RecyclerView) view;
            if (recyclerView.getLayoutManager() != null) {
                recyclerView.getLayoutManager().a(accessibilityEvent);
            }
        }
    }

    public androidx.core.f.a b() {
        return this.b;
    }

    public static class a extends androidx.core.f.a {
        final j a;

        public a(j jVar) {
            this.a = jVar;
        }

        @Override // androidx.core.f.a
        public void onInitializeAccessibilityNodeInfo(View view, c cVar) {
            super.onInitializeAccessibilityNodeInfo(view, cVar);
            if (!this.a.a() && this.a.a.getLayoutManager() != null) {
                this.a.a.getLayoutManager().a(view, cVar);
            }
        }

        @Override // androidx.core.f.a
        public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
            if (super.performAccessibilityAction(view, i, bundle)) {
                return true;
            }
            if (this.a.a() || this.a.a.getLayoutManager() == null) {
                return false;
            }
            return this.a.a.getLayoutManager().a(view, i, bundle);
        }
    }
}
