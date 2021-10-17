package androidx.recyclerview.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Observable;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.FocusFinder;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Interpolator;
import android.widget.EdgeEffect;
import android.widget.OverScroller;
import androidx.core.f.a.c;
import androidx.customview.view.AbsSavedState;
import androidx.recyclerview.R;
import androidx.recyclerview.widget.a;
import androidx.recyclerview.widget.b;
import androidx.recyclerview.widget.e;
import androidx.recyclerview.widget.m;
import androidx.recyclerview.widget.n;
import com.google.android.gms.auth.api.credentials.CredentialsApi;
import com.google.android.gms.common.api.Api;
import com.trusteer.tas.TasDefs;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.azeckoski.reflectutils.transcoders.JSONTranscoder;

public class RecyclerView extends ViewGroup implements androidx.core.f.j {
    static final boolean ALLOW_SIZE_IN_UNSPECIFIED_SPEC = (Build.VERSION.SDK_INT >= 23);
    static final boolean ALLOW_THREAD_GAP_WORK = (Build.VERSION.SDK_INT >= 21);
    private static final int[] CLIP_TO_PADDING_ATTR = {16842987};
    static final boolean DEBUG = false;
    static final int DEFAULT_ORIENTATION = 1;
    static final boolean DISPATCH_TEMP_DETACH = false;
    private static final boolean FORCE_ABS_FOCUS_SEARCH_DIRECTION = (Build.VERSION.SDK_INT <= 15);
    static final boolean FORCE_INVALIDATE_DISPLAY_LIST = (Build.VERSION.SDK_INT == 18 || Build.VERSION.SDK_INT == 19 || Build.VERSION.SDK_INT == 20);
    static final long FOREVER_NS = Long.MAX_VALUE;
    public static final int HORIZONTAL = 0;
    private static final boolean IGNORE_DETACHED_FOCUSED_CHILD = (Build.VERSION.SDK_INT <= 15);
    private static final int INVALID_POINTER = -1;
    public static final int INVALID_TYPE = -1;
    private static final Class<?>[] LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE = {Context.class, AttributeSet.class, Integer.TYPE, Integer.TYPE};
    static final int MAX_SCROLL_DURATION = 2000;
    private static final int[] NESTED_SCROLLING_ATTRS = {16843830};
    public static final long NO_ID = -1;
    public static final int NO_POSITION = -1;
    static final boolean POST_UPDATES_ON_ANIMATION = (Build.VERSION.SDK_INT >= 16);
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_SETTLING = 2;
    static final String TAG = "RecyclerView";
    public static final int TOUCH_SLOP_DEFAULT = 0;
    public static final int TOUCH_SLOP_PAGING = 1;
    static final String TRACE_BIND_VIEW_TAG = "RV OnBindView";
    static final String TRACE_CREATE_VIEW_TAG = "RV CreateView";
    private static final String TRACE_HANDLE_ADAPTER_UPDATES_TAG = "RV PartialInvalidate";
    static final String TRACE_NESTED_PREFETCH_TAG = "RV Nested Prefetch";
    private static final String TRACE_ON_DATA_SET_CHANGE_LAYOUT_TAG = "RV FullInvalidate";
    private static final String TRACE_ON_LAYOUT_TAG = "RV OnLayout";
    static final String TRACE_PREFETCH_TAG = "RV Prefetch";
    static final String TRACE_SCROLL_TAG = "RV Scroll";
    static final boolean VERBOSE_TRACING = false;
    public static final int VERTICAL = 1;
    static final Interpolator sQuinticInterpolator = new Interpolator() {
        /* class androidx.recyclerview.widget.RecyclerView.AnonymousClass3 */

        public float getInterpolation(float f) {
            float f2 = f - 1.0f;
            return (f2 * f2 * f2 * f2 * f2) + 1.0f;
        }
    };
    j mAccessibilityDelegate;
    private final AccessibilityManager mAccessibilityManager;
    private l mActiveOnItemTouchListener;
    a mAdapter;
    a mAdapterHelper;
    boolean mAdapterUpdateDuringMeasure;
    private EdgeEffect mBottomGlow;
    private d mChildDrawingOrderCallback;
    b mChildHelper;
    boolean mClipToPadding;
    boolean mDataSetHasChangedAfterLayout;
    boolean mDispatchItemsChangedEvent;
    private int mDispatchScrollCounter;
    private int mEatenAccessibilityChangeFlags;
    private e mEdgeEffectFactory;
    boolean mEnableFastScroller;
    boolean mFirstLayoutComplete;
    e mGapWorker;
    boolean mHasFixedSize;
    private boolean mIgnoreMotionEventTillDown;
    private int mInitialTouchX;
    private int mInitialTouchY;
    private int mInterceptRequestLayoutDepth;
    boolean mIsAttached;
    f mItemAnimator;
    private f.b mItemAnimatorListener;
    private Runnable mItemAnimatorRunner;
    final ArrayList<h> mItemDecorations;
    boolean mItemsAddedOrRemoved;
    boolean mItemsChanged;
    private int mLastTouchX;
    private int mLastTouchY;
    i mLayout;
    boolean mLayoutFrozen;
    private int mLayoutOrScrollCounter;
    boolean mLayoutWasDefered;
    private EdgeEffect mLeftGlow;
    private final int mMaxFlingVelocity;
    private final int mMinFlingVelocity;
    private final int[] mMinMaxLayoutPositions;
    private final int[] mNestedOffsets;
    private final q mObserver;
    private List<j> mOnChildAttachStateListeners;
    private k mOnFlingListener;
    private final ArrayList<l> mOnItemTouchListeners;
    final List<v> mPendingAccessibilityImportanceChange;
    private SavedState mPendingSavedState;
    boolean mPostedAnimatorRunner;
    e.a mPrefetchRegistry;
    private boolean mPreserveFocusAfterLayout;
    final o mRecycler;
    p mRecyclerListener;
    private EdgeEffect mRightGlow;
    private float mScaledHorizontalScrollFactor;
    private float mScaledVerticalScrollFactor;
    final int[] mScrollConsumed;
    private m mScrollListener;
    private List<m> mScrollListeners;
    private final int[] mScrollOffset;
    private int mScrollPointerId;
    private int mScrollState;
    final int[] mScrollStepConsumed;
    private androidx.core.f.k mScrollingChildHelper;
    final s mState;
    final Rect mTempRect;
    private final Rect mTempRect2;
    final RectF mTempRectF;
    private EdgeEffect mTopGlow;
    private int mTouchSlop;
    final Runnable mUpdateChildViewsRunnable;
    private VelocityTracker mVelocityTracker;
    final u mViewFlinger;
    private final n.b mViewInfoProcessCallback;
    final n mViewInfoStore;

    public interface d {
        int a(int i, int i2);
    }

    public interface j {
        void a(View view);

        void b(View view);
    }

    public static abstract class k {
        public abstract boolean a(int i, int i2);
    }

    public interface l {
        void a(boolean z);

        boolean a(RecyclerView recyclerView, MotionEvent motionEvent);

        void b(RecyclerView recyclerView, MotionEvent motionEvent);
    }

    public static abstract class m {
        public void a(RecyclerView recyclerView, int i) {
        }

        public void a(RecyclerView recyclerView, int i, int i2) {
        }
    }

    public interface p {
        void a(v vVar);
    }

    public static abstract class t {
        public abstract View a(o oVar, int i, int i2);
    }

    public void onChildAttachedToWindow(View view) {
    }

    public void onChildDetachedFromWindow(View view) {
    }

    public void onScrollStateChanged(int i2) {
    }

    public void onScrolled(int i2, int i3) {
    }

    public RecyclerView(Context context) {
        this(context, null);
    }

    public RecyclerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RecyclerView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mObserver = new q();
        this.mRecycler = new o();
        this.mViewInfoStore = new n();
        this.mUpdateChildViewsRunnable = new Runnable() {
            /* class androidx.recyclerview.widget.RecyclerView.AnonymousClass1 */

            public void run() {
                if (RecyclerView.this.mFirstLayoutComplete && !RecyclerView.this.isLayoutRequested()) {
                    if (!RecyclerView.this.mIsAttached) {
                        RecyclerView.this.requestLayout();
                    } else if (RecyclerView.this.mLayoutFrozen) {
                        RecyclerView.this.mLayoutWasDefered = true;
                    } else {
                        RecyclerView.this.consumePendingUpdateOperations();
                    }
                }
            }
        };
        this.mTempRect = new Rect();
        this.mTempRect2 = new Rect();
        this.mTempRectF = new RectF();
        this.mItemDecorations = new ArrayList<>();
        this.mOnItemTouchListeners = new ArrayList<>();
        this.mInterceptRequestLayoutDepth = 0;
        this.mDataSetHasChangedAfterLayout = false;
        this.mDispatchItemsChangedEvent = false;
        this.mLayoutOrScrollCounter = 0;
        this.mDispatchScrollCounter = 0;
        this.mEdgeEffectFactory = new e();
        this.mItemAnimator = new c();
        this.mScrollState = 0;
        this.mScrollPointerId = -1;
        this.mScaledHorizontalScrollFactor = Float.MIN_VALUE;
        this.mScaledVerticalScrollFactor = Float.MIN_VALUE;
        boolean z = true;
        this.mPreserveFocusAfterLayout = true;
        this.mViewFlinger = new u();
        this.mPrefetchRegistry = ALLOW_THREAD_GAP_WORK ? new e.a() : null;
        this.mState = new s();
        this.mItemsAddedOrRemoved = false;
        this.mItemsChanged = false;
        this.mItemAnimatorListener = new g();
        this.mPostedAnimatorRunner = false;
        this.mMinMaxLayoutPositions = new int[2];
        this.mScrollOffset = new int[2];
        this.mScrollConsumed = new int[2];
        this.mNestedOffsets = new int[2];
        this.mScrollStepConsumed = new int[2];
        this.mPendingAccessibilityImportanceChange = new ArrayList();
        this.mItemAnimatorRunner = new Runnable() {
            /* class androidx.recyclerview.widget.RecyclerView.AnonymousClass2 */

            public void run() {
                if (RecyclerView.this.mItemAnimator != null) {
                    RecyclerView.this.mItemAnimator.a();
                }
                RecyclerView.this.mPostedAnimatorRunner = false;
            }
        };
        this.mViewInfoProcessCallback = new n.b() {
            /* class androidx.recyclerview.widget.RecyclerView.AnonymousClass4 */

            @Override // androidx.recyclerview.widget.n.b
            public void a(v vVar, f.c cVar, f.c cVar2) {
                RecyclerView.this.mRecycler.c(vVar);
                RecyclerView.this.animateDisappearance(vVar, cVar, cVar2);
            }

            @Override // androidx.recyclerview.widget.n.b
            public void b(v vVar, f.c cVar, f.c cVar2) {
                RecyclerView.this.animateAppearance(vVar, cVar, cVar2);
            }

            @Override // androidx.recyclerview.widget.n.b
            public void c(v vVar, f.c cVar, f.c cVar2) {
                vVar.setIsRecyclable(false);
                if (RecyclerView.this.mDataSetHasChangedAfterLayout) {
                    if (RecyclerView.this.mItemAnimator.a(vVar, vVar, cVar, cVar2)) {
                        RecyclerView.this.postAnimationRunner();
                    }
                } else if (RecyclerView.this.mItemAnimator.c(vVar, cVar, cVar2)) {
                    RecyclerView.this.postAnimationRunner();
                }
            }

            @Override // androidx.recyclerview.widget.n.b
            public void a(v vVar) {
                RecyclerView.this.mLayout.a(vVar.itemView, RecyclerView.this.mRecycler);
            }
        };
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, CLIP_TO_PADDING_ATTR, i2, 0);
            this.mClipToPadding = obtainStyledAttributes.getBoolean(0, true);
            obtainStyledAttributes.recycle();
        } else {
            this.mClipToPadding = true;
        }
        setScrollContainer(true);
        setFocusableInTouchMode(true);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mScaledHorizontalScrollFactor = androidx.core.f.s.a(viewConfiguration, context);
        this.mScaledVerticalScrollFactor = androidx.core.f.s.b(viewConfiguration, context);
        this.mMinFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mMaxFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        setWillNotDraw(getOverScrollMode() == 2);
        this.mItemAnimator.a(this.mItemAnimatorListener);
        initAdapterManager();
        initChildrenHelper();
        initAutofill();
        if (androidx.core.f.r.e(this) == 0) {
            androidx.core.f.r.b(this, 1);
        }
        this.mAccessibilityManager = (AccessibilityManager) getContext().getSystemService("accessibility");
        setAccessibilityDelegateCompat(new j(this));
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, R.styleable.RecyclerView, i2, 0);
            String string = obtainStyledAttributes2.getString(R.styleable.RecyclerView_layoutManager);
            if (obtainStyledAttributes2.getInt(R.styleable.RecyclerView_android_descendantFocusability, -1) == -1) {
                setDescendantFocusability(262144);
            }
            this.mEnableFastScroller = obtainStyledAttributes2.getBoolean(R.styleable.RecyclerView_fastScrollEnabled, false);
            if (this.mEnableFastScroller) {
                initFastScroller((StateListDrawable) obtainStyledAttributes2.getDrawable(R.styleable.RecyclerView_fastScrollVerticalThumbDrawable), obtainStyledAttributes2.getDrawable(R.styleable.RecyclerView_fastScrollVerticalTrackDrawable), (StateListDrawable) obtainStyledAttributes2.getDrawable(R.styleable.RecyclerView_fastScrollHorizontalThumbDrawable), obtainStyledAttributes2.getDrawable(R.styleable.RecyclerView_fastScrollHorizontalTrackDrawable));
            }
            obtainStyledAttributes2.recycle();
            createLayoutManager(context, string, attributeSet, i2, 0);
            if (Build.VERSION.SDK_INT >= 21) {
                TypedArray obtainStyledAttributes3 = context.obtainStyledAttributes(attributeSet, NESTED_SCROLLING_ATTRS, i2, 0);
                boolean z2 = obtainStyledAttributes3.getBoolean(0, true);
                obtainStyledAttributes3.recycle();
                z = z2;
            }
        } else {
            setDescendantFocusability(262144);
        }
        setNestedScrollingEnabled(z);
    }

    /* access modifiers changed from: package-private */
    public String exceptionLabel() {
        return " " + super.toString() + ", adapter:" + this.mAdapter + ", layout:" + this.mLayout + ", context:" + getContext();
    }

    @SuppressLint({"InlinedApi"})
    private void initAutofill() {
        if (androidx.core.f.r.a(this) == 0) {
            androidx.core.f.r.a((View) this, 8);
        }
    }

    public j getCompatAccessibilityDelegate() {
        return this.mAccessibilityDelegate;
    }

    public void setAccessibilityDelegateCompat(j jVar) {
        this.mAccessibilityDelegate = jVar;
        androidx.core.f.r.a(this, this.mAccessibilityDelegate);
    }

    private void createLayoutManager(Context context, String str, AttributeSet attributeSet, int i2, int i3) {
        ClassLoader classLoader;
        Constructor<? extends U> constructor;
        if (str != null) {
            String trim = str.trim();
            if (!trim.isEmpty()) {
                String fullClassName = getFullClassName(context, trim);
                try {
                    if (isInEditMode()) {
                        classLoader = getClass().getClassLoader();
                    } else {
                        classLoader = context.getClassLoader();
                    }
                    Class<? extends U> asSubclass = classLoader.loadClass(fullClassName).asSubclass(i.class);
                    Object[] objArr = null;
                    try {
                        constructor = asSubclass.getConstructor(LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE);
                        objArr = new Object[]{context, attributeSet, Integer.valueOf(i2), Integer.valueOf(i3)};
                    } catch (NoSuchMethodException e2) {
                        try {
                            constructor = asSubclass.getConstructor(new Class[0]);
                        } catch (NoSuchMethodException e3) {
                            e3.initCause(e2);
                            throw new IllegalStateException(attributeSet.getPositionDescription() + ": Error creating LayoutManager " + fullClassName, e3);
                        }
                    }
                    constructor.setAccessible(true);
                    setLayoutManager((i) constructor.newInstance(objArr));
                } catch (ClassNotFoundException e4) {
                    throw new IllegalStateException(attributeSet.getPositionDescription() + ": Unable to find LayoutManager " + fullClassName, e4);
                } catch (InvocationTargetException e5) {
                    throw new IllegalStateException(attributeSet.getPositionDescription() + ": Could not instantiate the LayoutManager: " + fullClassName, e5);
                } catch (InstantiationException e6) {
                    throw new IllegalStateException(attributeSet.getPositionDescription() + ": Could not instantiate the LayoutManager: " + fullClassName, e6);
                } catch (IllegalAccessException e7) {
                    throw new IllegalStateException(attributeSet.getPositionDescription() + ": Cannot access non-public constructor " + fullClassName, e7);
                } catch (ClassCastException e8) {
                    throw new IllegalStateException(attributeSet.getPositionDescription() + ": Class is not a LayoutManager " + fullClassName, e8);
                }
            }
        }
    }

    private String getFullClassName(Context context, String str) {
        if (str.charAt(0) == '.') {
            return context.getPackageName() + str;
        } else if (str.contains(".")) {
            return str;
        } else {
            return RecyclerView.class.getPackage().getName() + '.' + str;
        }
    }

    private void initChildrenHelper() {
        this.mChildHelper = new b(new b.AbstractC٠٠٥٠b() {
            /* class androidx.recyclerview.widget.RecyclerView.AnonymousClass5 */

            @Override // androidx.recyclerview.widget.b.AbstractC٠٠٥٠b
            public int a() {
                return RecyclerView.this.getChildCount();
            }

            @Override // androidx.recyclerview.widget.b.AbstractC٠٠٥٠b
            public void a(View view, int i) {
                RecyclerView.this.addView(view, i);
                RecyclerView.this.dispatchChildAttached(view);
            }

            @Override // androidx.recyclerview.widget.b.AbstractC٠٠٥٠b
            public int a(View view) {
                return RecyclerView.this.indexOfChild(view);
            }

            @Override // androidx.recyclerview.widget.b.AbstractC٠٠٥٠b
            public void a(int i) {
                View childAt = RecyclerView.this.getChildAt(i);
                if (childAt != null) {
                    RecyclerView.this.dispatchChildDetached(childAt);
                    childAt.clearAnimation();
                }
                RecyclerView.this.removeViewAt(i);
            }

            @Override // androidx.recyclerview.widget.b.AbstractC٠٠٥٠b
            public View b(int i) {
                return RecyclerView.this.getChildAt(i);
            }

            @Override // androidx.recyclerview.widget.b.AbstractC٠٠٥٠b
            public void b() {
                int a2 = a();
                for (int i = 0; i < a2; i++) {
                    View b = b(i);
                    RecyclerView.this.dispatchChildDetached(b);
                    b.clearAnimation();
                }
                RecyclerView.this.removeAllViews();
            }

            @Override // androidx.recyclerview.widget.b.AbstractC٠٠٥٠b
            public v b(View view) {
                return RecyclerView.getChildViewHolderInt(view);
            }

            @Override // androidx.recyclerview.widget.b.AbstractC٠٠٥٠b
            public void a(View view, int i, ViewGroup.LayoutParams layoutParams) {
                v childViewHolderInt = RecyclerView.getChildViewHolderInt(view);
                if (childViewHolderInt != null) {
                    if (childViewHolderInt.isTmpDetached() || childViewHolderInt.shouldIgnore()) {
                        childViewHolderInt.clearTmpDetachFlag();
                    } else {
                        throw new IllegalArgumentException("Called attach on a child which is not detached: " + childViewHolderInt + RecyclerView.this.exceptionLabel());
                    }
                }
                RecyclerView.this.attachViewToParent(view, i, layoutParams);
            }

            @Override // androidx.recyclerview.widget.b.AbstractC٠٠٥٠b
            public void c(int i) {
                v childViewHolderInt;
                View b = b(i);
                if (!(b == null || (childViewHolderInt = RecyclerView.getChildViewHolderInt(b)) == null)) {
                    if (!childViewHolderInt.isTmpDetached() || childViewHolderInt.shouldIgnore()) {
                        childViewHolderInt.addFlags(256);
                    } else {
                        throw new IllegalArgumentException("called detach on an already detached child " + childViewHolderInt + RecyclerView.this.exceptionLabel());
                    }
                }
                RecyclerView.this.detachViewFromParent((RecyclerView) i);
            }

            @Override // androidx.recyclerview.widget.b.AbstractC٠٠٥٠b
            public void c(View view) {
                v childViewHolderInt = RecyclerView.getChildViewHolderInt(view);
                if (childViewHolderInt != null) {
                    childViewHolderInt.onEnteredHiddenState(RecyclerView.this);
                }
            }

            @Override // androidx.recyclerview.widget.b.AbstractC٠٠٥٠b
            public void d(View view) {
                v childViewHolderInt = RecyclerView.getChildViewHolderInt(view);
                if (childViewHolderInt != null) {
                    childViewHolderInt.onLeftHiddenState(RecyclerView.this);
                }
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void initAdapterManager() {
        this.mAdapterHelper = new a(new a.AbstractC٠٠٤٩a() {
            /* class androidx.recyclerview.widget.RecyclerView.AnonymousClass6 */

            @Override // androidx.recyclerview.widget.a.AbstractC٠٠٤٩a
            public v a(int i) {
                v findViewHolderForPosition = RecyclerView.this.findViewHolderForPosition(i, true);
                if (findViewHolderForPosition != null && !RecyclerView.this.mChildHelper.c(findViewHolderForPosition.itemView)) {
                    return findViewHolderForPosition;
                }
                return null;
            }

            @Override // androidx.recyclerview.widget.a.AbstractC٠٠٤٩a
            public void a(int i, int i2) {
                RecyclerView.this.offsetPositionRecordsForRemove(i, i2, true);
                RecyclerView recyclerView = RecyclerView.this;
                recyclerView.mItemsAddedOrRemoved = true;
                recyclerView.mState.c += i2;
            }

            @Override // androidx.recyclerview.widget.a.AbstractC٠٠٤٩a
            public void b(int i, int i2) {
                RecyclerView.this.offsetPositionRecordsForRemove(i, i2, false);
                RecyclerView.this.mItemsAddedOrRemoved = true;
            }

            @Override // androidx.recyclerview.widget.a.AbstractC٠٠٤٩a
            public void a(int i, int i2, Object obj) {
                RecyclerView.this.viewRangeUpdate(i, i2, obj);
                RecyclerView.this.mItemsChanged = true;
            }

            @Override // androidx.recyclerview.widget.a.AbstractC٠٠٤٩a
            public void a(a.b bVar) {
                c(bVar);
            }

            /* access modifiers changed from: package-private */
            public void c(a.b bVar) {
                int i = bVar.a;
                if (i == 4) {
                    RecyclerView.this.mLayout.a(RecyclerView.this, bVar.b, bVar.d, bVar.c);
                } else if (i != 8) {
                    switch (i) {
                        case 1:
                            RecyclerView.this.mLayout.a(RecyclerView.this, bVar.b, bVar.d);
                            return;
                        case 2:
                            RecyclerView.this.mLayout.b(RecyclerView.this, bVar.b, bVar.d);
                            return;
                        default:
                            return;
                    }
                } else {
                    RecyclerView.this.mLayout.a(RecyclerView.this, bVar.b, bVar.d, 1);
                }
            }

            @Override // androidx.recyclerview.widget.a.AbstractC٠٠٤٩a
            public void b(a.b bVar) {
                c(bVar);
            }

            @Override // androidx.recyclerview.widget.a.AbstractC٠٠٤٩a
            public void c(int i, int i2) {
                RecyclerView.this.offsetPositionRecordsForInsert(i, i2);
                RecyclerView.this.mItemsAddedOrRemoved = true;
            }

            @Override // androidx.recyclerview.widget.a.AbstractC٠٠٤٩a
            public void d(int i, int i2) {
                RecyclerView.this.offsetPositionRecordsForMove(i, i2);
                RecyclerView.this.mItemsAddedOrRemoved = true;
            }
        });
    }

    public void setHasFixedSize(boolean z) {
        this.mHasFixedSize = z;
    }

    public boolean hasFixedSize() {
        return this.mHasFixedSize;
    }

    public void setClipToPadding(boolean z) {
        if (z != this.mClipToPadding) {
            invalidateGlows();
        }
        this.mClipToPadding = z;
        super.setClipToPadding(z);
        if (this.mFirstLayoutComplete) {
            requestLayout();
        }
    }

    public boolean getClipToPadding() {
        return this.mClipToPadding;
    }

    public void setScrollingTouchSlop(int i2) {
        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
        switch (i2) {
            case 0:
                break;
            case 1:
                this.mTouchSlop = viewConfiguration.getScaledPagingTouchSlop();
                return;
            default:
                Log.w(TAG, "setScrollingTouchSlop(): bad argument constant " + i2 + "; using default value");
                break;
        }
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
    }

    public void swapAdapter(a aVar, boolean z) {
        setLayoutFrozen(false);
        setAdapterInternal(aVar, true, z);
        processDataSetCompletelyChanged(true);
        requestLayout();
    }

    public void setAdapter(a aVar) {
        setLayoutFrozen(false);
        setAdapterInternal(aVar, false, true);
        processDataSetCompletelyChanged(false);
        requestLayout();
    }

    /* access modifiers changed from: package-private */
    public void removeAndRecycleViews() {
        f fVar = this.mItemAnimator;
        if (fVar != null) {
            fVar.d();
        }
        i iVar = this.mLayout;
        if (iVar != null) {
            iVar.c(this.mRecycler);
            this.mLayout.b(this.mRecycler);
        }
        this.mRecycler.a();
    }

    private void setAdapterInternal(a aVar, boolean z, boolean z2) {
        a aVar2 = this.mAdapter;
        if (aVar2 != null) {
            aVar2.unregisterAdapterDataObserver(this.mObserver);
            this.mAdapter.onDetachedFromRecyclerView(this);
        }
        if (!z || z2) {
            removeAndRecycleViews();
        }
        this.mAdapterHelper.a();
        a aVar3 = this.mAdapter;
        this.mAdapter = aVar;
        if (aVar != null) {
            aVar.registerAdapterDataObserver(this.mObserver);
            aVar.onAttachedToRecyclerView(this);
        }
        i iVar = this.mLayout;
        if (iVar != null) {
            iVar.a(aVar3, this.mAdapter);
        }
        this.mRecycler.a(aVar3, this.mAdapter, z);
        this.mState.f = true;
    }

    public a getAdapter() {
        return this.mAdapter;
    }

    public void setRecyclerListener(p pVar) {
        this.mRecyclerListener = pVar;
    }

    public int getBaseline() {
        i iVar = this.mLayout;
        if (iVar != null) {
            return iVar.u();
        }
        return super.getBaseline();
    }

    public void addOnChildAttachStateChangeListener(j jVar) {
        if (this.mOnChildAttachStateListeners == null) {
            this.mOnChildAttachStateListeners = new ArrayList();
        }
        this.mOnChildAttachStateListeners.add(jVar);
    }

    public void removeOnChildAttachStateChangeListener(j jVar) {
        List<j> list = this.mOnChildAttachStateListeners;
        if (list != null) {
            list.remove(jVar);
        }
    }

    public void clearOnChildAttachStateChangeListeners() {
        List<j> list = this.mOnChildAttachStateListeners;
        if (list != null) {
            list.clear();
        }
    }

    public void setLayoutManager(i iVar) {
        if (iVar != this.mLayout) {
            stopScroll();
            if (this.mLayout != null) {
                f fVar = this.mItemAnimator;
                if (fVar != null) {
                    fVar.d();
                }
                this.mLayout.c(this.mRecycler);
                this.mLayout.b(this.mRecycler);
                this.mRecycler.a();
                if (this.mIsAttached) {
                    this.mLayout.b(this, this.mRecycler);
                }
                this.mLayout.b((RecyclerView) null);
                this.mLayout = null;
            } else {
                this.mRecycler.a();
            }
            this.mChildHelper.a();
            this.mLayout = iVar;
            if (iVar != null) {
                if (iVar.q == null) {
                    this.mLayout.b(this);
                    if (this.mIsAttached) {
                        this.mLayout.c(this);
                    }
                } else {
                    throw new IllegalArgumentException("LayoutManager " + iVar + " is already attached to a RecyclerView:" + iVar.q.exceptionLabel());
                }
            }
            this.mRecycler.b();
            requestLayout();
        }
    }

    public void setOnFlingListener(k kVar) {
        this.mOnFlingListener = kVar;
    }

    public k getOnFlingListener() {
        return this.mOnFlingListener;
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        SavedState savedState2 = this.mPendingSavedState;
        if (savedState2 != null) {
            savedState.a(savedState2);
        } else {
            i iVar = this.mLayout;
            if (iVar != null) {
                savedState.a = iVar.d();
            } else {
                savedState.a = null;
            }
        }
        return savedState;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        this.mPendingSavedState = (SavedState) parcelable;
        super.onRestoreInstanceState(this.mPendingSavedState.getSuperState());
        if (this.mLayout != null && this.mPendingSavedState.a != null) {
            this.mLayout.a(this.mPendingSavedState.a);
        }
    }

    /* access modifiers changed from: protected */
    @Override // android.view.View, android.view.ViewGroup
    public void dispatchSaveInstanceState(SparseArray<Parcelable> sparseArray) {
        dispatchFreezeSelfOnly(sparseArray);
    }

    /* access modifiers changed from: protected */
    @Override // android.view.View, android.view.ViewGroup
    public void dispatchRestoreInstanceState(SparseArray<Parcelable> sparseArray) {
        dispatchThawSelfOnly(sparseArray);
    }

    private void addAnimatingView(v vVar) {
        View view = vVar.itemView;
        boolean z = view.getParent() == this;
        this.mRecycler.c(getChildViewHolder(view));
        if (vVar.isTmpDetached()) {
            this.mChildHelper.a(view, -1, view.getLayoutParams(), true);
        } else if (!z) {
            this.mChildHelper.a(view, true);
        } else {
            this.mChildHelper.d(view);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean removeAnimatingView(View view) {
        startInterceptRequestLayout();
        boolean f2 = this.mChildHelper.f(view);
        if (f2) {
            v childViewHolderInt = getChildViewHolderInt(view);
            this.mRecycler.c(childViewHolderInt);
            this.mRecycler.b(childViewHolderInt);
        }
        stopInterceptRequestLayout(!f2);
        return f2;
    }

    public i getLayoutManager() {
        return this.mLayout;
    }

    public n getRecycledViewPool() {
        return this.mRecycler.g();
    }

    public void setRecycledViewPool(n nVar) {
        this.mRecycler.a(nVar);
    }

    public void setViewCacheExtension(t tVar) {
        this.mRecycler.a(tVar);
    }

    public void setItemViewCacheSize(int i2) {
        this.mRecycler.a(i2);
    }

    public int getScrollState() {
        return this.mScrollState;
    }

    /* access modifiers changed from: package-private */
    public void setScrollState(int i2) {
        if (i2 != this.mScrollState) {
            this.mScrollState = i2;
            if (i2 != 2) {
                stopScrollersInternal();
            }
            dispatchOnScrollStateChanged(i2);
        }
    }

    public void addItemDecoration(h hVar, int i2) {
        i iVar = this.mLayout;
        if (iVar != null) {
            iVar.a("Cannot add item decoration during a scroll  or layout");
        }
        if (this.mItemDecorations.isEmpty()) {
            setWillNotDraw(false);
        }
        if (i2 < 0) {
            this.mItemDecorations.add(hVar);
        } else {
            this.mItemDecorations.add(i2, hVar);
        }
        markItemDecorInsetsDirty();
        requestLayout();
    }

    public void addItemDecoration(h hVar) {
        addItemDecoration(hVar, -1);
    }

    public h getItemDecorationAt(int i2) {
        int itemDecorationCount = getItemDecorationCount();
        if (i2 >= 0 && i2 < itemDecorationCount) {
            return this.mItemDecorations.get(i2);
        }
        throw new IndexOutOfBoundsException(i2 + " is an invalid index for size " + itemDecorationCount);
    }

    public int getItemDecorationCount() {
        return this.mItemDecorations.size();
    }

    public void removeItemDecorationAt(int i2) {
        int itemDecorationCount = getItemDecorationCount();
        if (i2 < 0 || i2 >= itemDecorationCount) {
            throw new IndexOutOfBoundsException(i2 + " is an invalid index for size " + itemDecorationCount);
        }
        removeItemDecoration(getItemDecorationAt(i2));
    }

    public void removeItemDecoration(h hVar) {
        i iVar = this.mLayout;
        if (iVar != null) {
            iVar.a("Cannot remove item decoration during a scroll  or layout");
        }
        this.mItemDecorations.remove(hVar);
        if (this.mItemDecorations.isEmpty()) {
            setWillNotDraw(getOverScrollMode() == 2);
        }
        markItemDecorInsetsDirty();
        requestLayout();
    }

    public void setChildDrawingOrderCallback(d dVar) {
        if (dVar != this.mChildDrawingOrderCallback) {
            this.mChildDrawingOrderCallback = dVar;
            setChildrenDrawingOrderEnabled(this.mChildDrawingOrderCallback != null);
        }
    }

    @Deprecated
    public void setOnScrollListener(m mVar) {
        this.mScrollListener = mVar;
    }

    public void addOnScrollListener(m mVar) {
        if (this.mScrollListeners == null) {
            this.mScrollListeners = new ArrayList();
        }
        this.mScrollListeners.add(mVar);
    }

    public void removeOnScrollListener(m mVar) {
        List<m> list = this.mScrollListeners;
        if (list != null) {
            list.remove(mVar);
        }
    }

    public void clearOnScrollListeners() {
        List<m> list = this.mScrollListeners;
        if (list != null) {
            list.clear();
        }
    }

    public void scrollToPosition(int i2) {
        if (!this.mLayoutFrozen) {
            stopScroll();
            i iVar = this.mLayout;
            if (iVar == null) {
                Log.e(TAG, "Cannot scroll to position a LayoutManager set. Call setLayoutManager with a non-null argument.");
                return;
            }
            iVar.e(i2);
            awakenScrollBars();
        }
    }

    /* access modifiers changed from: package-private */
    public void jumpToPositionForSmoothScroller(int i2) {
        i iVar = this.mLayout;
        if (iVar != null) {
            iVar.e(i2);
            awakenScrollBars();
        }
    }

    public void smoothScrollToPosition(int i2) {
        if (!this.mLayoutFrozen) {
            i iVar = this.mLayout;
            if (iVar == null) {
                Log.e(TAG, "Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            } else {
                iVar.a(this, this.mState, i2);
            }
        }
    }

    public void scrollTo(int i2, int i3) {
        Log.w(TAG, "RecyclerView does not support scrolling to an absolute position. Use scrollToPosition instead");
    }

    public void scrollBy(int i2, int i3) {
        i iVar = this.mLayout;
        if (iVar == null) {
            Log.e(TAG, "Cannot scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
        } else if (!this.mLayoutFrozen) {
            boolean e2 = iVar.e();
            boolean f2 = this.mLayout.f();
            if (e2 || f2) {
                if (!e2) {
                    i2 = 0;
                }
                if (!f2) {
                    i3 = 0;
                }
                scrollByInternal(i2, i3, null);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void scrollStep(int i2, int i3, int[] iArr) {
        startInterceptRequestLayout();
        onEnterLayoutOrScroll();
        androidx.core.os.c.a(TRACE_SCROLL_TAG);
        fillRemainingScrollValues(this.mState);
        int a2 = i2 != 0 ? this.mLayout.a(i2, this.mRecycler, this.mState) : 0;
        int b2 = i3 != 0 ? this.mLayout.b(i3, this.mRecycler, this.mState) : 0;
        androidx.core.os.c.a();
        repositionShadowingViews();
        onExitLayoutOrScroll();
        stopInterceptRequestLayout(false);
        if (iArr != null) {
            iArr[0] = a2;
            iArr[1] = b2;
        }
    }

    /* access modifiers changed from: package-private */
    public void consumePendingUpdateOperations() {
        if (!this.mFirstLayoutComplete || this.mDataSetHasChangedAfterLayout) {
            androidx.core.os.c.a(TRACE_ON_DATA_SET_CHANGE_LAYOUT_TAG);
            dispatchLayout();
            androidx.core.os.c.a();
        } else if (this.mAdapterHelper.d()) {
            if (this.mAdapterHelper.a(4) && !this.mAdapterHelper.a(11)) {
                androidx.core.os.c.a(TRACE_HANDLE_ADAPTER_UPDATES_TAG);
                startInterceptRequestLayout();
                onEnterLayoutOrScroll();
                this.mAdapterHelper.b();
                if (!this.mLayoutWasDefered) {
                    if (hasUpdatedView()) {
                        dispatchLayout();
                    } else {
                        this.mAdapterHelper.c();
                    }
                }
                stopInterceptRequestLayout(true);
                onExitLayoutOrScroll();
                androidx.core.os.c.a();
            } else if (this.mAdapterHelper.d()) {
                androidx.core.os.c.a(TRACE_ON_DATA_SET_CHANGE_LAYOUT_TAG);
                dispatchLayout();
                androidx.core.os.c.a();
            }
        }
    }

    private boolean hasUpdatedView() {
        int b2 = this.mChildHelper.b();
        for (int i2 = 0; i2 < b2; i2++) {
            v childViewHolderInt = getChildViewHolderInt(this.mChildHelper.b(i2));
            if (!(childViewHolderInt == null || childViewHolderInt.shouldIgnore() || !childViewHolderInt.isUpdated())) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x009b, code lost:
        if (r0 != 0) goto L_0x00a0;
     */
    public boolean scrollByInternal(int i2, int i3, MotionEvent motionEvent) {
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        consumePendingUpdateOperations();
        if (this.mAdapter != null) {
            scrollStep(i2, i3, this.mScrollStepConsumed);
            int[] iArr = this.mScrollStepConsumed;
            int i9 = iArr[0];
            int i10 = iArr[1];
            i7 = i10;
            i4 = i9;
            i6 = i2 - i9;
            i5 = i3 - i10;
        } else {
            i7 = 0;
            i6 = 0;
            i5 = 0;
            i4 = 0;
        }
        if (!this.mItemDecorations.isEmpty()) {
            invalidate();
        }
        if (dispatchNestedScroll(i4, i7, i6, i5, this.mScrollOffset, 0)) {
            int i11 = this.mLastTouchX;
            int[] iArr2 = this.mScrollOffset;
            this.mLastTouchX = i11 - iArr2[0];
            this.mLastTouchY -= iArr2[1];
            if (motionEvent != null) {
                motionEvent.offsetLocation((float) iArr2[0], (float) iArr2[1]);
            }
            int[] iArr3 = this.mNestedOffsets;
            int i12 = iArr3[0];
            int[] iArr4 = this.mScrollOffset;
            iArr3[0] = i12 + iArr4[0];
            iArr3[1] = iArr3[1] + iArr4[1];
        } else if (getOverScrollMode() != 2) {
            if (motionEvent != null && !androidx.core.f.h.a(motionEvent, 8194)) {
                pullGlows(motionEvent.getX(), (float) i6, motionEvent.getY(), (float) i5);
            }
            considerReleasingGlowsOnScroll(i2, i3);
        }
        if (i4 == 0) {
            i8 = i7;
        } else {
            i8 = i7;
        }
        dispatchOnScrolled(i4, i8);
        if (!awakenScrollBars()) {
            invalidate();
        }
        if (i4 == 0 && i8 == 0) {
            return false;
        }
        return true;
    }

    public int computeHorizontalScrollOffset() {
        i iVar = this.mLayout;
        if (iVar != null && iVar.e()) {
            return this.mLayout.c(this.mState);
        }
        return 0;
    }

    public int computeHorizontalScrollExtent() {
        i iVar = this.mLayout;
        if (iVar != null && iVar.e()) {
            return this.mLayout.e(this.mState);
        }
        return 0;
    }

    public int computeHorizontalScrollRange() {
        i iVar = this.mLayout;
        if (iVar != null && iVar.e()) {
            return this.mLayout.g(this.mState);
        }
        return 0;
    }

    public int computeVerticalScrollOffset() {
        i iVar = this.mLayout;
        if (iVar != null && iVar.f()) {
            return this.mLayout.d(this.mState);
        }
        return 0;
    }

    public int computeVerticalScrollExtent() {
        i iVar = this.mLayout;
        if (iVar != null && iVar.f()) {
            return this.mLayout.f(this.mState);
        }
        return 0;
    }

    public int computeVerticalScrollRange() {
        i iVar = this.mLayout;
        if (iVar != null && iVar.f()) {
            return this.mLayout.h(this.mState);
        }
        return 0;
    }

    /* access modifiers changed from: package-private */
    public void startInterceptRequestLayout() {
        this.mInterceptRequestLayoutDepth++;
        if (this.mInterceptRequestLayoutDepth == 1 && !this.mLayoutFrozen) {
            this.mLayoutWasDefered = false;
        }
    }

    /* access modifiers changed from: package-private */
    public void stopInterceptRequestLayout(boolean z) {
        if (this.mInterceptRequestLayoutDepth < 1) {
            this.mInterceptRequestLayoutDepth = 1;
        }
        if (!z && !this.mLayoutFrozen) {
            this.mLayoutWasDefered = false;
        }
        if (this.mInterceptRequestLayoutDepth == 1) {
            if (z && this.mLayoutWasDefered && !this.mLayoutFrozen && this.mLayout != null && this.mAdapter != null) {
                dispatchLayout();
            }
            if (!this.mLayoutFrozen) {
                this.mLayoutWasDefered = false;
            }
        }
        this.mInterceptRequestLayoutDepth--;
    }

    public void setLayoutFrozen(boolean z) {
        if (z != this.mLayoutFrozen) {
            assertNotInLayoutOrScroll("Do not setLayoutFrozen in layout or scroll");
            if (!z) {
                this.mLayoutFrozen = false;
                if (!(!this.mLayoutWasDefered || this.mLayout == null || this.mAdapter == null)) {
                    requestLayout();
                }
                this.mLayoutWasDefered = false;
                return;
            }
            long uptimeMillis = SystemClock.uptimeMillis();
            onTouchEvent(MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, 0.0f, 0.0f, 0));
            this.mLayoutFrozen = true;
            this.mIgnoreMotionEventTillDown = true;
            stopScroll();
        }
    }

    public boolean isLayoutFrozen() {
        return this.mLayoutFrozen;
    }

    public void smoothScrollBy(int i2, int i3) {
        smoothScrollBy(i2, i3, null);
    }

    public void smoothScrollBy(int i2, int i3, Interpolator interpolator) {
        i iVar = this.mLayout;
        if (iVar == null) {
            Log.e(TAG, "Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
        } else if (!this.mLayoutFrozen) {
            if (!iVar.e()) {
                i2 = 0;
            }
            if (!this.mLayout.f()) {
                i3 = 0;
            }
            if (i2 != 0 || i3 != 0) {
                this.mViewFlinger.a(i2, i3, interpolator);
            }
        }
    }

    public boolean fling(int i2, int i3) {
        i iVar = this.mLayout;
        int i4 = 0;
        if (iVar == null) {
            Log.e(TAG, "Cannot fling without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return false;
        } else if (this.mLayoutFrozen) {
            return false;
        } else {
            boolean e2 = iVar.e();
            boolean f2 = this.mLayout.f();
            if (!e2 || Math.abs(i2) < this.mMinFlingVelocity) {
                i2 = 0;
            }
            if (!f2 || Math.abs(i3) < this.mMinFlingVelocity) {
                i3 = 0;
            }
            if (i2 == 0 && i3 == 0) {
                return false;
            }
            float f3 = (float) i2;
            float f4 = (float) i3;
            if (!dispatchNestedPreFling(f3, f4)) {
                boolean z = e2 || f2;
                dispatchNestedFling(f3, f4, z);
                k kVar = this.mOnFlingListener;
                if (kVar != null && kVar.a(i2, i3)) {
                    return true;
                }
                if (z) {
                    if (e2) {
                        i4 = 1;
                    }
                    if (f2) {
                        i4 |= 2;
                    }
                    startNestedScroll(i4, 1);
                    int i5 = this.mMaxFlingVelocity;
                    int max = Math.max(-i5, Math.min(i2, i5));
                    int i6 = this.mMaxFlingVelocity;
                    this.mViewFlinger.a(max, Math.max(-i6, Math.min(i3, i6)));
                    return true;
                }
            }
            return false;
        }
    }

    public void stopScroll() {
        setScrollState(0);
        stopScrollersInternal();
    }

    private void stopScrollersInternal() {
        this.mViewFlinger.b();
        i iVar = this.mLayout;
        if (iVar != null) {
            iVar.H();
        }
    }

    public int getMinFlingVelocity() {
        return this.mMinFlingVelocity;
    }

    public int getMaxFlingVelocity() {
        return this.mMaxFlingVelocity;
    }

    private void pullGlows(float f2, float f3, float f4, float f5) {
        boolean z;
        boolean z2 = true;
        if (f3 < 0.0f) {
            ensureLeftGlow();
            androidx.core.widget.d.a(this.mLeftGlow, (-f3) / ((float) getWidth()), 1.0f - (f4 / ((float) getHeight())));
            z = true;
        } else if (f3 > 0.0f) {
            ensureRightGlow();
            androidx.core.widget.d.a(this.mRightGlow, f3 / ((float) getWidth()), f4 / ((float) getHeight()));
            z = true;
        } else {
            z = false;
        }
        if (f5 < 0.0f) {
            ensureTopGlow();
            androidx.core.widget.d.a(this.mTopGlow, (-f5) / ((float) getHeight()), f2 / ((float) getWidth()));
        } else if (f5 > 0.0f) {
            ensureBottomGlow();
            androidx.core.widget.d.a(this.mBottomGlow, f5 / ((float) getHeight()), 1.0f - (f2 / ((float) getWidth())));
        } else {
            z2 = z;
        }
        if (z2 || f3 != 0.0f || f5 != 0.0f) {
            androidx.core.f.r.d(this);
        }
    }

    private void releaseGlows() {
        boolean z;
        EdgeEffect edgeEffect = this.mLeftGlow;
        if (edgeEffect != null) {
            edgeEffect.onRelease();
            z = this.mLeftGlow.isFinished();
        } else {
            z = false;
        }
        EdgeEffect edgeEffect2 = this.mTopGlow;
        if (edgeEffect2 != null) {
            edgeEffect2.onRelease();
            z |= this.mTopGlow.isFinished();
        }
        EdgeEffect edgeEffect3 = this.mRightGlow;
        if (edgeEffect3 != null) {
            edgeEffect3.onRelease();
            z |= this.mRightGlow.isFinished();
        }
        EdgeEffect edgeEffect4 = this.mBottomGlow;
        if (edgeEffect4 != null) {
            edgeEffect4.onRelease();
            z |= this.mBottomGlow.isFinished();
        }
        if (z) {
            androidx.core.f.r.d(this);
        }
    }

    /* access modifiers changed from: package-private */
    public void considerReleasingGlowsOnScroll(int i2, int i3) {
        boolean z;
        EdgeEffect edgeEffect = this.mLeftGlow;
        if (edgeEffect == null || edgeEffect.isFinished() || i2 <= 0) {
            z = false;
        } else {
            this.mLeftGlow.onRelease();
            z = this.mLeftGlow.isFinished();
        }
        EdgeEffect edgeEffect2 = this.mRightGlow;
        if (edgeEffect2 != null && !edgeEffect2.isFinished() && i2 < 0) {
            this.mRightGlow.onRelease();
            z |= this.mRightGlow.isFinished();
        }
        EdgeEffect edgeEffect3 = this.mTopGlow;
        if (edgeEffect3 != null && !edgeEffect3.isFinished() && i3 > 0) {
            this.mTopGlow.onRelease();
            z |= this.mTopGlow.isFinished();
        }
        EdgeEffect edgeEffect4 = this.mBottomGlow;
        if (edgeEffect4 != null && !edgeEffect4.isFinished() && i3 < 0) {
            this.mBottomGlow.onRelease();
            z |= this.mBottomGlow.isFinished();
        }
        if (z) {
            androidx.core.f.r.d(this);
        }
    }

    /* access modifiers changed from: package-private */
    public void absorbGlows(int i2, int i3) {
        if (i2 < 0) {
            ensureLeftGlow();
            this.mLeftGlow.onAbsorb(-i2);
        } else if (i2 > 0) {
            ensureRightGlow();
            this.mRightGlow.onAbsorb(i2);
        }
        if (i3 < 0) {
            ensureTopGlow();
            this.mTopGlow.onAbsorb(-i3);
        } else if (i3 > 0) {
            ensureBottomGlow();
            this.mBottomGlow.onAbsorb(i3);
        }
        if (i2 != 0 || i3 != 0) {
            androidx.core.f.r.d(this);
        }
    }

    /* access modifiers changed from: package-private */
    public void ensureLeftGlow() {
        if (this.mLeftGlow == null) {
            this.mLeftGlow = this.mEdgeEffectFactory.a(this, 0);
            if (this.mClipToPadding) {
                this.mLeftGlow.setSize((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight());
            } else {
                this.mLeftGlow.setSize(getMeasuredHeight(), getMeasuredWidth());
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void ensureRightGlow() {
        if (this.mRightGlow == null) {
            this.mRightGlow = this.mEdgeEffectFactory.a(this, 2);
            if (this.mClipToPadding) {
                this.mRightGlow.setSize((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight());
            } else {
                this.mRightGlow.setSize(getMeasuredHeight(), getMeasuredWidth());
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void ensureTopGlow() {
        if (this.mTopGlow == null) {
            this.mTopGlow = this.mEdgeEffectFactory.a(this, 1);
            if (this.mClipToPadding) {
                this.mTopGlow.setSize((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom());
            } else {
                this.mTopGlow.setSize(getMeasuredWidth(), getMeasuredHeight());
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void ensureBottomGlow() {
        if (this.mBottomGlow == null) {
            this.mBottomGlow = this.mEdgeEffectFactory.a(this, 3);
            if (this.mClipToPadding) {
                this.mBottomGlow.setSize((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom());
            } else {
                this.mBottomGlow.setSize(getMeasuredWidth(), getMeasuredHeight());
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void invalidateGlows() {
        this.mBottomGlow = null;
        this.mTopGlow = null;
        this.mRightGlow = null;
        this.mLeftGlow = null;
    }

    public void setEdgeEffectFactory(e eVar) {
        androidx.core.e.f.a(eVar);
        this.mEdgeEffectFactory = eVar;
        invalidateGlows();
    }

    public e getEdgeEffectFactory() {
        return this.mEdgeEffectFactory;
    }

    public View focusSearch(View view, int i2) {
        View view2;
        boolean z;
        View d2 = this.mLayout.d(view, i2);
        if (d2 != null) {
            return d2;
        }
        boolean z2 = this.mAdapter != null && this.mLayout != null && !isComputingLayout() && !this.mLayoutFrozen;
        FocusFinder instance = FocusFinder.getInstance();
        if (!z2 || !(i2 == 2 || i2 == 1)) {
            View findNextFocus = instance.findNextFocus(this, view, i2);
            if (findNextFocus != null || !z2) {
                view2 = findNextFocus;
            } else {
                consumePendingUpdateOperations();
                if (findContainingItemView(view) == null) {
                    return null;
                }
                startInterceptRequestLayout();
                view2 = this.mLayout.a(view, i2, this.mRecycler, this.mState);
                stopInterceptRequestLayout(false);
            }
        } else {
            if (this.mLayout.f()) {
                int i3 = i2 == 2 ? 130 : 33;
                z = instance.findNextFocus(this, view, i3) == null;
                if (FORCE_ABS_FOCUS_SEARCH_DIRECTION) {
                    i2 = i3;
                }
            } else {
                z = false;
            }
            if (!z && this.mLayout.e()) {
                int i4 = (this.mLayout.t() == 1) ^ (i2 == 2) ? 66 : 17;
                z = instance.findNextFocus(this, view, i4) == null;
                if (FORCE_ABS_FOCUS_SEARCH_DIRECTION) {
                    i2 = i4;
                }
            }
            if (z) {
                consumePendingUpdateOperations();
                if (findContainingItemView(view) == null) {
                    return null;
                }
                startInterceptRequestLayout();
                this.mLayout.a(view, i2, this.mRecycler, this.mState);
                stopInterceptRequestLayout(false);
            }
            view2 = instance.findNextFocus(this, view, i2);
        }
        if (view2 == null || view2.hasFocusable()) {
            return isPreferredNextFocus(view, view2, i2) ? view2 : super.focusSearch(view, i2);
        }
        if (getFocusedChild() == null) {
            return super.focusSearch(view, i2);
        }
        requestChildOnScreen(view2, null);
        return view;
    }

    private boolean isPreferredNextFocus(View view, View view2, int i2) {
        int i3;
        if (view2 == null || view2 == this || findContainingItemView(view2) == null) {
            return false;
        }
        if (view == null || findContainingItemView(view) == null) {
            return true;
        }
        this.mTempRect.set(0, 0, view.getWidth(), view.getHeight());
        this.mTempRect2.set(0, 0, view2.getWidth(), view2.getHeight());
        offsetDescendantRectToMyCoords(view, this.mTempRect);
        offsetDescendantRectToMyCoords(view2, this.mTempRect2);
        char c2 = 65535;
        int i4 = this.mLayout.t() == 1 ? -1 : 1;
        if ((this.mTempRect.left < this.mTempRect2.left || this.mTempRect.right <= this.mTempRect2.left) && this.mTempRect.right < this.mTempRect2.right) {
            i3 = 1;
        } else {
            i3 = ((this.mTempRect.right > this.mTempRect2.right || this.mTempRect.left >= this.mTempRect2.right) && this.mTempRect.left > this.mTempRect2.left) ? -1 : 0;
        }
        if ((this.mTempRect.top < this.mTempRect2.top || this.mTempRect.bottom <= this.mTempRect2.top) && this.mTempRect.bottom < this.mTempRect2.bottom) {
            c2 = 1;
        } else if ((this.mTempRect.bottom <= this.mTempRect2.bottom && this.mTempRect.top < this.mTempRect2.bottom) || this.mTempRect.top <= this.mTempRect2.top) {
            c2 = 0;
        }
        if (i2 != 17) {
            if (i2 != 33) {
                if (i2 != 66) {
                    if (i2 != 130) {
                        switch (i2) {
                            case 1:
                                if (c2 < 0 || (c2 == 0 && i3 * i4 <= 0)) {
                                    return true;
                                }
                                return false;
                            case 2:
                                if (c2 > 0 || (c2 == 0 && i3 * i4 >= 0)) {
                                    return true;
                                }
                                return false;
                            default:
                                throw new IllegalArgumentException("Invalid direction: " + i2 + exceptionLabel());
                        }
                    } else if (c2 > 0) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (i3 > 0) {
                    return true;
                } else {
                    return false;
                }
            } else if (c2 < 0) {
                return true;
            } else {
                return false;
            }
        } else if (i3 < 0) {
            return true;
        } else {
            return false;
        }
    }

    public void requestChildFocus(View view, View view2) {
        if (!this.mLayout.a(this, this.mState, view, view2) && view2 != null) {
            requestChildOnScreen(view, view2);
        }
        super.requestChildFocus(view, view2);
    }

    private void requestChildOnScreen(View view, View view2) {
        View view3 = view2 != null ? view2 : view;
        this.mTempRect.set(0, 0, view3.getWidth(), view3.getHeight());
        ViewGroup.LayoutParams layoutParams = view3.getLayoutParams();
        if (layoutParams instanceof LayoutParams) {
            LayoutParams layoutParams2 = (LayoutParams) layoutParams;
            if (!layoutParams2.e) {
                Rect rect = layoutParams2.d;
                this.mTempRect.left -= rect.left;
                this.mTempRect.right += rect.right;
                this.mTempRect.top -= rect.top;
                this.mTempRect.bottom += rect.bottom;
            }
        }
        if (view2 != null) {
            offsetDescendantRectToMyCoords(view2, this.mTempRect);
            offsetRectIntoDescendantCoords(view, this.mTempRect);
        }
        this.mLayout.a(this, view, this.mTempRect, !this.mFirstLayoutComplete, view2 == null);
    }

    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z) {
        return this.mLayout.a(this, view, rect, z);
    }

    @Override // android.view.View, android.view.ViewGroup
    public void addFocusables(ArrayList<View> arrayList, int i2, int i3) {
        i iVar = this.mLayout;
        if (iVar == null || !iVar.a(this, arrayList, i2, i3)) {
            super.addFocusables(arrayList, i2, i3);
        }
    }

    /* access modifiers changed from: protected */
    public boolean onRequestFocusInDescendants(int i2, Rect rect) {
        if (isComputingLayout()) {
            return false;
        }
        return super.onRequestFocusInDescendants(i2, rect);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x004f, code lost:
        if (r0 >= 30.0f) goto L_0x0054;
     */
    public void onAttachedToWindow() {
        float f2;
        super.onAttachedToWindow();
        this.mLayoutOrScrollCounter = 0;
        boolean z = true;
        this.mIsAttached = true;
        if (!this.mFirstLayoutComplete || isLayoutRequested()) {
            z = false;
        }
        this.mFirstLayoutComplete = z;
        i iVar = this.mLayout;
        if (iVar != null) {
            iVar.c(this);
        }
        this.mPostedAnimatorRunner = false;
        if (ALLOW_THREAD_GAP_WORK) {
            this.mGapWorker = e.a.get();
            if (this.mGapWorker == null) {
                this.mGapWorker = new e();
                Display C = androidx.core.f.r.C(this);
                if (!isInEditMode() && C != null) {
                    f2 = C.getRefreshRate();
                }
                f2 = 60.0f;
                this.mGapWorker.d = (long) (1.0E9f / f2);
                e.a.set(this.mGapWorker);
            }
            this.mGapWorker.a(this);
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        e eVar;
        super.onDetachedFromWindow();
        f fVar = this.mItemAnimator;
        if (fVar != null) {
            fVar.d();
        }
        stopScroll();
        this.mIsAttached = false;
        i iVar = this.mLayout;
        if (iVar != null) {
            iVar.b(this, this.mRecycler);
        }
        this.mPendingAccessibilityImportanceChange.clear();
        removeCallbacks(this.mItemAnimatorRunner);
        this.mViewInfoStore.b();
        if (ALLOW_THREAD_GAP_WORK && (eVar = this.mGapWorker) != null) {
            eVar.b(this);
            this.mGapWorker = null;
        }
    }

    public boolean isAttachedToWindow() {
        return this.mIsAttached;
    }

    /* access modifiers changed from: package-private */
    public void assertInLayoutOrScroll(String str) {
        if (isComputingLayout()) {
            return;
        }
        if (str == null) {
            throw new IllegalStateException("Cannot call this method unless RecyclerView is computing a layout or scrolling" + exceptionLabel());
        }
        throw new IllegalStateException(str + exceptionLabel());
    }

    /* access modifiers changed from: package-private */
    public void assertNotInLayoutOrScroll(String str) {
        if (isComputingLayout()) {
            if (str == null) {
                throw new IllegalStateException("Cannot call this method while RecyclerView is computing a layout or scrolling" + exceptionLabel());
            }
            throw new IllegalStateException(str);
        } else if (this.mDispatchScrollCounter > 0) {
            Log.w(TAG, "Cannot call this method in a scroll callback. Scroll callbacks mightbe run during a measure & layout pass where you cannot change theRecyclerView data. Any method call that might change the structureof the RecyclerView or the adapter contents should be postponed tothe next frame.", new IllegalStateException("" + exceptionLabel()));
        }
    }

    public void addOnItemTouchListener(l lVar) {
        this.mOnItemTouchListeners.add(lVar);
    }

    public void removeOnItemTouchListener(l lVar) {
        this.mOnItemTouchListeners.remove(lVar);
        if (this.mActiveOnItemTouchListener == lVar) {
            this.mActiveOnItemTouchListener = null;
        }
    }

    private boolean dispatchOnItemTouchIntercept(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 3 || action == 0) {
            this.mActiveOnItemTouchListener = null;
        }
        int size = this.mOnItemTouchListeners.size();
        for (int i2 = 0; i2 < size; i2++) {
            l lVar = this.mOnItemTouchListeners.get(i2);
            if (lVar.a(this, motionEvent) && action != 3) {
                this.mActiveOnItemTouchListener = lVar;
                return true;
            }
        }
        return false;
    }

    private boolean dispatchOnItemTouch(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        l lVar = this.mActiveOnItemTouchListener;
        if (lVar != null) {
            if (action == 0) {
                this.mActiveOnItemTouchListener = null;
            } else {
                lVar.b(this, motionEvent);
                if (action == 3 || action == 1) {
                    this.mActiveOnItemTouchListener = null;
                }
                return true;
            }
        }
        if (action != 0) {
            int size = this.mOnItemTouchListeners.size();
            for (int i2 = 0; i2 < size; i2++) {
                l lVar2 = this.mOnItemTouchListeners.get(i2);
                if (lVar2.a(this, motionEvent)) {
                    this.mActiveOnItemTouchListener = lVar2;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean z;
        if (this.mLayoutFrozen) {
            return false;
        }
        if (dispatchOnItemTouchIntercept(motionEvent)) {
            cancelTouch();
            return true;
        }
        i iVar = this.mLayout;
        if (iVar == null) {
            return false;
        }
        boolean e2 = iVar.e();
        boolean f2 = this.mLayout.f();
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        int actionIndex = motionEvent.getActionIndex();
        switch (actionMasked) {
            case 0:
                if (this.mIgnoreMotionEventTillDown) {
                    this.mIgnoreMotionEventTillDown = false;
                }
                this.mScrollPointerId = motionEvent.getPointerId(0);
                int x = (int) (motionEvent.getX() + 0.5f);
                this.mLastTouchX = x;
                this.mInitialTouchX = x;
                int y = (int) (motionEvent.getY() + 0.5f);
                this.mLastTouchY = y;
                this.mInitialTouchY = y;
                if (this.mScrollState == 2) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    setScrollState(1);
                }
                int[] iArr = this.mNestedOffsets;
                iArr[1] = 0;
                iArr[0] = 0;
                int i2 = e2 ? 1 : 0;
                if (f2) {
                    i2 |= 2;
                }
                startNestedScroll(i2, 0);
                break;
            case 1:
                this.mVelocityTracker.clear();
                stopNestedScroll(0);
                break;
            case 2:
                int findPointerIndex = motionEvent.findPointerIndex(this.mScrollPointerId);
                if (findPointerIndex >= 0) {
                    int x2 = (int) (motionEvent.getX(findPointerIndex) + 0.5f);
                    int y2 = (int) (motionEvent.getY(findPointerIndex) + 0.5f);
                    if (this.mScrollState != 1) {
                        int i3 = x2 - this.mInitialTouchX;
                        int i4 = y2 - this.mInitialTouchY;
                        if (!e2 || Math.abs(i3) <= this.mTouchSlop) {
                            z = false;
                        } else {
                            this.mLastTouchX = x2;
                            z = true;
                        }
                        if (f2 && Math.abs(i4) > this.mTouchSlop) {
                            this.mLastTouchY = y2;
                            z = true;
                        }
                        if (z) {
                            setScrollState(1);
                            break;
                        }
                    }
                } else {
                    Log.e(TAG, "Error processing scroll; pointer index for id " + this.mScrollPointerId + " not found. Did any MotionEvents get skipped?");
                    return false;
                }
                break;
            case 3:
                cancelTouch();
                break;
            case 5:
                this.mScrollPointerId = motionEvent.getPointerId(actionIndex);
                int x3 = (int) (motionEvent.getX(actionIndex) + 0.5f);
                this.mLastTouchX = x3;
                this.mInitialTouchX = x3;
                int y3 = (int) (motionEvent.getY(actionIndex) + 0.5f);
                this.mLastTouchY = y3;
                this.mInitialTouchY = y3;
                break;
            case 6:
                onPointerUp(motionEvent);
                break;
        }
        if (this.mScrollState == 1) {
            return true;
        }
        return false;
    }

    public void requestDisallowInterceptTouchEvent(boolean z) {
        int size = this.mOnItemTouchListeners.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.mOnItemTouchListeners.get(i2).a(z);
        }
        super.requestDisallowInterceptTouchEvent(z);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z;
        int i2;
        int i3;
        boolean z2 = false;
        if (this.mLayoutFrozen || this.mIgnoreMotionEventTillDown) {
            return false;
        }
        if (dispatchOnItemTouch(motionEvent)) {
            cancelTouch();
            return true;
        }
        i iVar = this.mLayout;
        if (iVar == null) {
            return false;
        }
        boolean e2 = iVar.e();
        boolean f2 = this.mLayout.f();
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        MotionEvent obtain = MotionEvent.obtain(motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        int actionIndex = motionEvent.getActionIndex();
        if (actionMasked == 0) {
            int[] iArr = this.mNestedOffsets;
            iArr[1] = 0;
            iArr[0] = 0;
        }
        int[] iArr2 = this.mNestedOffsets;
        obtain.offsetLocation((float) iArr2[0], (float) iArr2[1]);
        switch (actionMasked) {
            case 0:
                this.mScrollPointerId = motionEvent.getPointerId(0);
                int x = (int) (motionEvent.getX() + 0.5f);
                this.mLastTouchX = x;
                this.mInitialTouchX = x;
                int y = (int) (motionEvent.getY() + 0.5f);
                this.mLastTouchY = y;
                this.mInitialTouchY = y;
                int i4 = e2 ? 1 : 0;
                if (f2) {
                    i4 |= 2;
                }
                startNestedScroll(i4, 0);
                break;
            case 1:
                this.mVelocityTracker.addMovement(obtain);
                this.mVelocityTracker.computeCurrentVelocity(CredentialsApi.ACTIVITY_RESULT_ADD_ACCOUNT, (float) this.mMaxFlingVelocity);
                float f3 = e2 ? -this.mVelocityTracker.getXVelocity(this.mScrollPointerId) : 0.0f;
                float f4 = f2 ? -this.mVelocityTracker.getYVelocity(this.mScrollPointerId) : 0.0f;
                if ((f3 == 0.0f && f4 == 0.0f) || !fling((int) f3, (int) f4)) {
                    setScrollState(0);
                }
                resetTouch();
                z2 = true;
                break;
            case 2:
                int findPointerIndex = motionEvent.findPointerIndex(this.mScrollPointerId);
                if (findPointerIndex >= 0) {
                    int x2 = (int) (motionEvent.getX(findPointerIndex) + 0.5f);
                    int y2 = (int) (motionEvent.getY(findPointerIndex) + 0.5f);
                    int i5 = this.mLastTouchX - x2;
                    int i6 = this.mLastTouchY - y2;
                    if (dispatchNestedPreScroll(i5, i6, this.mScrollConsumed, this.mScrollOffset, 0)) {
                        int[] iArr3 = this.mScrollConsumed;
                        i5 -= iArr3[0];
                        i6 -= iArr3[1];
                        int[] iArr4 = this.mScrollOffset;
                        obtain.offsetLocation((float) iArr4[0], (float) iArr4[1]);
                        int[] iArr5 = this.mNestedOffsets;
                        int i7 = iArr5[0];
                        int[] iArr6 = this.mScrollOffset;
                        iArr5[0] = i7 + iArr6[0];
                        iArr5[1] = iArr5[1] + iArr6[1];
                    }
                    if (this.mScrollState != 1) {
                        if (!e2 || Math.abs(i5) <= (i3 = this.mTouchSlop)) {
                            z = false;
                        } else {
                            i5 = i5 > 0 ? i5 - i3 : i5 + i3;
                            z = true;
                        }
                        if (f2 && Math.abs(i6) > (i2 = this.mTouchSlop)) {
                            i6 = i6 > 0 ? i6 - i2 : i6 + i2;
                            z = true;
                        }
                        if (z) {
                            setScrollState(1);
                        }
                    }
                    if (this.mScrollState == 1) {
                        int[] iArr7 = this.mScrollOffset;
                        this.mLastTouchX = x2 - iArr7[0];
                        this.mLastTouchY = y2 - iArr7[1];
                        if (scrollByInternal(e2 ? i5 : 0, f2 ? i6 : 0, obtain)) {
                            getParent().requestDisallowInterceptTouchEvent(true);
                        }
                        if (!(this.mGapWorker == null || (i5 == 0 && i6 == 0))) {
                            this.mGapWorker.a(this, i5, i6);
                            break;
                        }
                    }
                } else {
                    Log.e(TAG, "Error processing scroll; pointer index for id " + this.mScrollPointerId + " not found. Did any MotionEvents get skipped?");
                    return false;
                }
                break;
            case 3:
                cancelTouch();
                break;
            case 5:
                this.mScrollPointerId = motionEvent.getPointerId(actionIndex);
                int x3 = (int) (motionEvent.getX(actionIndex) + 0.5f);
                this.mLastTouchX = x3;
                this.mInitialTouchX = x3;
                int y3 = (int) (motionEvent.getY(actionIndex) + 0.5f);
                this.mLastTouchY = y3;
                this.mInitialTouchY = y3;
                break;
            case 6:
                onPointerUp(motionEvent);
                break;
        }
        if (!z2) {
            this.mVelocityTracker.addMovement(obtain);
        }
        obtain.recycle();
        return true;
    }

    private void resetTouch() {
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.clear();
        }
        stopNestedScroll(0);
        releaseGlows();
    }

    private void cancelTouch() {
        resetTouch();
        setScrollState(0);
    }

    private void onPointerUp(MotionEvent motionEvent) {
        int actionIndex = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(actionIndex) == this.mScrollPointerId) {
            int i2 = actionIndex == 0 ? 1 : 0;
            this.mScrollPointerId = motionEvent.getPointerId(i2);
            int x = (int) (motionEvent.getX(i2) + 0.5f);
            this.mLastTouchX = x;
            this.mInitialTouchX = x;
            int y = (int) (motionEvent.getY(i2) + 0.5f);
            this.mLastTouchY = y;
            this.mInitialTouchY = y;
        }
    }

    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        float f2;
        float f3;
        if (this.mLayout != null && !this.mLayoutFrozen && motionEvent.getAction() == 8) {
            if ((motionEvent.getSource() & 2) != 0) {
                f3 = this.mLayout.f() ? -motionEvent.getAxisValue(9) : 0.0f;
                f2 = this.mLayout.e() ? motionEvent.getAxisValue(10) : 0.0f;
            } else if ((motionEvent.getSource() & 4194304) != 0) {
                float axisValue = motionEvent.getAxisValue(26);
                if (this.mLayout.f()) {
                    f3 = -axisValue;
                    f2 = 0.0f;
                } else if (this.mLayout.e()) {
                    f2 = axisValue;
                    f3 = 0.0f;
                } else {
                    f3 = 0.0f;
                    f2 = 0.0f;
                }
            } else {
                f3 = 0.0f;
                f2 = 0.0f;
            }
            if (!(f3 == 0.0f && f2 == 0.0f)) {
                scrollByInternal((int) (f2 * this.mScaledHorizontalScrollFactor), (int) (f3 * this.mScaledVerticalScrollFactor), motionEvent);
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        i iVar = this.mLayout;
        if (iVar == null) {
            defaultOnMeasure(i2, i3);
            return;
        }
        boolean z = false;
        if (iVar.c()) {
            int mode = View.MeasureSpec.getMode(i2);
            int mode2 = View.MeasureSpec.getMode(i3);
            this.mLayout.a(this.mRecycler, this.mState, i2, i3);
            if (mode == 1073741824 && mode2 == 1073741824) {
                z = true;
            }
            if (!z && this.mAdapter != null) {
                if (this.mState.d == 1) {
                    dispatchLayoutStep1();
                }
                this.mLayout.c(i2, i3);
                this.mState.i = true;
                dispatchLayoutStep2();
                this.mLayout.d(i2, i3);
                if (this.mLayout.l()) {
                    this.mLayout.c(View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824));
                    this.mState.i = true;
                    dispatchLayoutStep2();
                    this.mLayout.d(i2, i3);
                }
            }
        } else if (this.mHasFixedSize) {
            this.mLayout.a(this.mRecycler, this.mState, i2, i3);
        } else {
            if (this.mAdapterUpdateDuringMeasure) {
                startInterceptRequestLayout();
                onEnterLayoutOrScroll();
                processAdapterUpdatesAndSetAnimationFlags();
                onExitLayoutOrScroll();
                if (this.mState.k) {
                    this.mState.g = true;
                } else {
                    this.mAdapterHelper.e();
                    this.mState.g = false;
                }
                this.mAdapterUpdateDuringMeasure = false;
                stopInterceptRequestLayout(false);
            } else if (this.mState.k) {
                setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
                return;
            }
            a aVar = this.mAdapter;
            if (aVar != null) {
                this.mState.e = aVar.getItemCount();
            } else {
                this.mState.e = 0;
            }
            startInterceptRequestLayout();
            this.mLayout.a(this.mRecycler, this.mState, i2, i3);
            stopInterceptRequestLayout(false);
            this.mState.g = false;
        }
    }

    /* access modifiers changed from: package-private */
    public void defaultOnMeasure(int i2, int i3) {
        setMeasuredDimension(i.a(i2, getPaddingLeft() + getPaddingRight(), androidx.core.f.r.j(this)), i.a(i3, getPaddingTop() + getPaddingBottom(), androidx.core.f.r.k(this)));
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i2, int i3, int i4, int i5) {
        super.onSizeChanged(i2, i3, i4, i5);
        if (i2 != i4 || i3 != i5) {
            invalidateGlows();
        }
    }

    public void setItemAnimator(f fVar) {
        f fVar2 = this.mItemAnimator;
        if (fVar2 != null) {
            fVar2.d();
            this.mItemAnimator.a(null);
        }
        this.mItemAnimator = fVar;
        f fVar3 = this.mItemAnimator;
        if (fVar3 != null) {
            fVar3.a(this.mItemAnimatorListener);
        }
    }

    /* access modifiers changed from: package-private */
    public void onEnterLayoutOrScroll() {
        this.mLayoutOrScrollCounter++;
    }

    /* access modifiers changed from: package-private */
    public void onExitLayoutOrScroll() {
        onExitLayoutOrScroll(true);
    }

    /* access modifiers changed from: package-private */
    public void onExitLayoutOrScroll(boolean z) {
        this.mLayoutOrScrollCounter--;
        if (this.mLayoutOrScrollCounter < 1) {
            this.mLayoutOrScrollCounter = 0;
            if (z) {
                dispatchContentChangedIfNecessary();
                dispatchPendingImportantForAccessibilityChanges();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isAccessibilityEnabled() {
        AccessibilityManager accessibilityManager = this.mAccessibilityManager;
        return accessibilityManager != null && accessibilityManager.isEnabled();
    }

    private void dispatchContentChangedIfNecessary() {
        int i2 = this.mEatenAccessibilityChangeFlags;
        this.mEatenAccessibilityChangeFlags = 0;
        if (i2 != 0 && isAccessibilityEnabled()) {
            AccessibilityEvent obtain = AccessibilityEvent.obtain();
            obtain.setEventType(2048);
            androidx.core.f.a.a.a(obtain, i2);
            sendAccessibilityEventUnchecked(obtain);
        }
    }

    public boolean isComputingLayout() {
        return this.mLayoutOrScrollCounter > 0;
    }

    /* access modifiers changed from: package-private */
    public boolean shouldDeferAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (!isComputingLayout()) {
            return false;
        }
        int a2 = accessibilityEvent != null ? androidx.core.f.a.a.a(accessibilityEvent) : 0;
        if (a2 == 0) {
            a2 = 0;
        }
        this.mEatenAccessibilityChangeFlags = a2 | this.mEatenAccessibilityChangeFlags;
        return true;
    }

    public void sendAccessibilityEventUnchecked(AccessibilityEvent accessibilityEvent) {
        if (!shouldDeferAccessibilityEvent(accessibilityEvent)) {
            super.sendAccessibilityEventUnchecked(accessibilityEvent);
        }
    }

    public f getItemAnimator() {
        return this.mItemAnimator;
    }

    /* access modifiers changed from: package-private */
    public void postAnimationRunner() {
        if (!this.mPostedAnimatorRunner && this.mIsAttached) {
            androidx.core.f.r.a(this, this.mItemAnimatorRunner);
            this.mPostedAnimatorRunner = true;
        }
    }

    private boolean predictiveItemAnimationsEnabled() {
        return this.mItemAnimator != null && this.mLayout.b();
    }

    private void processAdapterUpdatesAndSetAnimationFlags() {
        if (this.mDataSetHasChangedAfterLayout) {
            this.mAdapterHelper.a();
            if (this.mDispatchItemsChangedEvent) {
                this.mLayout.a(this);
            }
        }
        if (predictiveItemAnimationsEnabled()) {
            this.mAdapterHelper.b();
        } else {
            this.mAdapterHelper.e();
        }
        boolean z = false;
        boolean z2 = this.mItemsAddedOrRemoved || this.mItemsChanged;
        this.mState.j = this.mFirstLayoutComplete && this.mItemAnimator != null && (this.mDataSetHasChangedAfterLayout || z2 || this.mLayout.u) && (!this.mDataSetHasChangedAfterLayout || this.mAdapter.hasStableIds());
        s sVar = this.mState;
        if (sVar.j && z2 && !this.mDataSetHasChangedAfterLayout && predictiveItemAnimationsEnabled()) {
            z = true;
        }
        sVar.k = z;
    }

    /* access modifiers changed from: package-private */
    public void dispatchLayout() {
        if (this.mAdapter == null) {
            Log.e(TAG, "No adapter attached; skipping layout");
        } else if (this.mLayout == null) {
            Log.e(TAG, "No layout manager attached; skipping layout");
        } else {
            s sVar = this.mState;
            sVar.i = false;
            if (sVar.d == 1) {
                dispatchLayoutStep1();
                this.mLayout.f(this);
                dispatchLayoutStep2();
            } else if (!this.mAdapterHelper.f() && this.mLayout.y() == getWidth() && this.mLayout.z() == getHeight()) {
                this.mLayout.f(this);
            } else {
                this.mLayout.f(this);
                dispatchLayoutStep2();
            }
            dispatchLayoutStep3();
        }
    }

    private void saveFocusInfo() {
        int i2;
        v vVar = null;
        View focusedChild = (!this.mPreserveFocusAfterLayout || !hasFocus() || this.mAdapter == null) ? null : getFocusedChild();
        if (focusedChild != null) {
            vVar = findContainingViewHolder(focusedChild);
        }
        if (vVar == null) {
            resetFocusInfo();
            return;
        }
        this.mState.m = this.mAdapter.hasStableIds() ? vVar.getItemId() : -1;
        s sVar = this.mState;
        if (this.mDataSetHasChangedAfterLayout) {
            i2 = -1;
        } else if (vVar.isRemoved()) {
            i2 = vVar.mOldPosition;
        } else {
            i2 = vVar.getAdapterPosition();
        }
        sVar.l = i2;
        this.mState.n = getDeepestFocusedViewWithId(vVar.itemView);
    }

    private void resetFocusInfo() {
        s sVar = this.mState;
        sVar.m = -1;
        sVar.l = -1;
        sVar.n = -1;
    }

    private View findNextViewToFocus() {
        v findViewHolderForAdapterPosition;
        int i2 = this.mState.l != -1 ? this.mState.l : 0;
        int e2 = this.mState.e();
        for (int i3 = i2; i3 < e2; i3++) {
            v findViewHolderForAdapterPosition2 = findViewHolderForAdapterPosition(i3);
            if (findViewHolderForAdapterPosition2 == null) {
                break;
            } else if (findViewHolderForAdapterPosition2.itemView.hasFocusable()) {
                return findViewHolderForAdapterPosition2.itemView;
            }
        }
        int min = Math.min(e2, i2);
        while (true) {
            min--;
            if (min < 0 || (findViewHolderForAdapterPosition = findViewHolderForAdapterPosition(min)) == null) {
                return null;
            }
            if (findViewHolderForAdapterPosition.itemView.hasFocusable()) {
                return findViewHolderForAdapterPosition.itemView;
            }
        }
    }

    private void recoverFocusFromState() {
        View view;
        if (this.mPreserveFocusAfterLayout && this.mAdapter != null && hasFocus() && getDescendantFocusability() != 393216) {
            if (getDescendantFocusability() != 131072 || !isFocused()) {
                if (!isFocused()) {
                    View focusedChild = getFocusedChild();
                    if (!IGNORE_DETACHED_FOCUSED_CHILD || (focusedChild.getParent() != null && focusedChild.hasFocus())) {
                        if (!this.mChildHelper.c(focusedChild)) {
                            return;
                        }
                    } else if (this.mChildHelper.b() == 0) {
                        requestFocus();
                        return;
                    }
                }
                View view2 = null;
                v findViewHolderForItemId = (this.mState.m == -1 || !this.mAdapter.hasStableIds()) ? null : findViewHolderForItemId(this.mState.m);
                if (findViewHolderForItemId != null && !this.mChildHelper.c(findViewHolderForItemId.itemView) && findViewHolderForItemId.itemView.hasFocusable()) {
                    view2 = findViewHolderForItemId.itemView;
                } else if (this.mChildHelper.b() > 0) {
                    view2 = findNextViewToFocus();
                }
                if (view2 != null) {
                    if (((long) this.mState.n) == -1 || (view = view2.findViewById(this.mState.n)) == null || !view.isFocusable()) {
                        view = view2;
                    }
                    view.requestFocus();
                }
            }
        }
    }

    private int getDeepestFocusedViewWithId(View view) {
        int id = view.getId();
        while (!view.isFocused() && (view instanceof ViewGroup) && view.hasFocus()) {
            view = ((ViewGroup) view).getFocusedChild();
            if (view.getId() != -1) {
                id = view.getId();
            }
        }
        return id;
    }

    /* access modifiers changed from: package-private */
    public final void fillRemainingScrollValues(s sVar) {
        if (getScrollState() == 2) {
            OverScroller overScroller = this.mViewFlinger.a;
            sVar.o = overScroller.getFinalX() - overScroller.getCurrX();
            sVar.p = overScroller.getFinalY() - overScroller.getCurrY();
            return;
        }
        sVar.o = 0;
        sVar.p = 0;
    }

    private void dispatchLayoutStep1() {
        boolean z = true;
        this.mState.a(1);
        fillRemainingScrollValues(this.mState);
        this.mState.i = false;
        startInterceptRequestLayout();
        this.mViewInfoStore.a();
        onEnterLayoutOrScroll();
        processAdapterUpdatesAndSetAnimationFlags();
        saveFocusInfo();
        s sVar = this.mState;
        if (!sVar.j || !this.mItemsChanged) {
            z = false;
        }
        sVar.h = z;
        this.mItemsChanged = false;
        this.mItemsAddedOrRemoved = false;
        s sVar2 = this.mState;
        sVar2.g = sVar2.k;
        this.mState.e = this.mAdapter.getItemCount();
        findMinMaxChildLayoutPositions(this.mMinMaxLayoutPositions);
        if (this.mState.j) {
            int b2 = this.mChildHelper.b();
            for (int i2 = 0; i2 < b2; i2++) {
                v childViewHolderInt = getChildViewHolderInt(this.mChildHelper.b(i2));
                if (!childViewHolderInt.shouldIgnore() && (!childViewHolderInt.isInvalid() || this.mAdapter.hasStableIds())) {
                    this.mViewInfoStore.a(childViewHolderInt, this.mItemAnimator.a(this.mState, childViewHolderInt, f.e(childViewHolderInt), childViewHolderInt.getUnmodifiedPayloads()));
                    if (this.mState.h && childViewHolderInt.isUpdated() && !childViewHolderInt.isRemoved() && !childViewHolderInt.shouldIgnore() && !childViewHolderInt.isInvalid()) {
                        this.mViewInfoStore.a(getChangedHolderKey(childViewHolderInt), childViewHolderInt);
                    }
                }
            }
        }
        if (this.mState.k) {
            saveOldPositions();
            boolean z2 = this.mState.f;
            s sVar3 = this.mState;
            sVar3.f = false;
            this.mLayout.c(this.mRecycler, sVar3);
            this.mState.f = z2;
            for (int i3 = 0; i3 < this.mChildHelper.b(); i3++) {
                v childViewHolderInt2 = getChildViewHolderInt(this.mChildHelper.b(i3));
                if (!childViewHolderInt2.shouldIgnore() && !this.mViewInfoStore.d(childViewHolderInt2)) {
                    int e2 = f.e(childViewHolderInt2);
                    boolean hasAnyOfTheFlags = childViewHolderInt2.hasAnyOfTheFlags(8192);
                    if (!hasAnyOfTheFlags) {
                        e2 |= TasDefs.ADDITIONAL_DATA_MAX_LENGTH;
                    }
                    f.c a2 = this.mItemAnimator.a(this.mState, childViewHolderInt2, e2, childViewHolderInt2.getUnmodifiedPayloads());
                    if (hasAnyOfTheFlags) {
                        recordAnimationInfoIfBouncedHiddenView(childViewHolderInt2, a2);
                    } else {
                        this.mViewInfoStore.b(childViewHolderInt2, a2);
                    }
                }
            }
            clearOldPositions();
        } else {
            clearOldPositions();
        }
        onExitLayoutOrScroll();
        stopInterceptRequestLayout(false);
        this.mState.d = 2;
    }

    private void dispatchLayoutStep2() {
        startInterceptRequestLayout();
        onEnterLayoutOrScroll();
        this.mState.a(6);
        this.mAdapterHelper.e();
        this.mState.e = this.mAdapter.getItemCount();
        s sVar = this.mState;
        sVar.c = 0;
        sVar.g = false;
        this.mLayout.c(this.mRecycler, sVar);
        s sVar2 = this.mState;
        sVar2.f = false;
        this.mPendingSavedState = null;
        sVar2.j = sVar2.j && this.mItemAnimator != null;
        this.mState.d = 4;
        onExitLayoutOrScroll();
        stopInterceptRequestLayout(false);
    }

    private void dispatchLayoutStep3() {
        this.mState.a(4);
        startInterceptRequestLayout();
        onEnterLayoutOrScroll();
        s sVar = this.mState;
        sVar.d = 1;
        if (sVar.j) {
            for (int b2 = this.mChildHelper.b() - 1; b2 >= 0; b2--) {
                v childViewHolderInt = getChildViewHolderInt(this.mChildHelper.b(b2));
                if (!childViewHolderInt.shouldIgnore()) {
                    long changedHolderKey = getChangedHolderKey(childViewHolderInt);
                    f.c a2 = this.mItemAnimator.a(this.mState, childViewHolderInt);
                    v a3 = this.mViewInfoStore.a(changedHolderKey);
                    if (a3 == null || a3.shouldIgnore()) {
                        this.mViewInfoStore.c(childViewHolderInt, a2);
                    } else {
                        boolean a4 = this.mViewInfoStore.a(a3);
                        boolean a5 = this.mViewInfoStore.a(childViewHolderInt);
                        if (!a4 || a3 != childViewHolderInt) {
                            f.c b3 = this.mViewInfoStore.b(a3);
                            this.mViewInfoStore.c(childViewHolderInt, a2);
                            f.c c2 = this.mViewInfoStore.c(childViewHolderInt);
                            if (b3 == null) {
                                handleMissingPreInfoForChangeError(changedHolderKey, childViewHolderInt, a3);
                            } else {
                                animateChange(a3, childViewHolderInt, b3, c2, a4, a5);
                            }
                        } else {
                            this.mViewInfoStore.c(childViewHolderInt, a2);
                        }
                    }
                }
            }
            this.mViewInfoStore.a(this.mViewInfoProcessCallback);
        }
        this.mLayout.b(this.mRecycler);
        s sVar2 = this.mState;
        sVar2.b = sVar2.e;
        this.mDataSetHasChangedAfterLayout = false;
        this.mDispatchItemsChangedEvent = false;
        s sVar3 = this.mState;
        sVar3.j = false;
        sVar3.k = false;
        this.mLayout.u = false;
        if (this.mRecycler.b != null) {
            this.mRecycler.b.clear();
        }
        if (this.mLayout.y) {
            i iVar = this.mLayout;
            iVar.x = 0;
            iVar.y = false;
            this.mRecycler.b();
        }
        this.mLayout.a(this.mState);
        onExitLayoutOrScroll();
        stopInterceptRequestLayout(false);
        this.mViewInfoStore.a();
        int[] iArr = this.mMinMaxLayoutPositions;
        if (didChildRangeChange(iArr[0], iArr[1])) {
            dispatchOnScrolled(0, 0);
        }
        recoverFocusFromState();
        resetFocusInfo();
    }

    private void handleMissingPreInfoForChangeError(long j2, v vVar, v vVar2) {
        int b2 = this.mChildHelper.b();
        for (int i2 = 0; i2 < b2; i2++) {
            v childViewHolderInt = getChildViewHolderInt(this.mChildHelper.b(i2));
            if (childViewHolderInt != vVar && getChangedHolderKey(childViewHolderInt) == j2) {
                a aVar = this.mAdapter;
                if (aVar == null || !aVar.hasStableIds()) {
                    throw new IllegalStateException("Two different ViewHolders have the same change ID. This might happen due to inconsistent Adapter update events or if the LayoutManager lays out the same View multiple times.\n ViewHolder 1:" + childViewHolderInt + " \n View Holder 2:" + vVar + exceptionLabel());
                }
                throw new IllegalStateException("Two different ViewHolders have the same stable ID. Stable IDs in your adapter MUST BE unique and SHOULD NOT change.\n ViewHolder 1:" + childViewHolderInt + " \n View Holder 2:" + vVar + exceptionLabel());
            }
        }
        Log.e(TAG, "Problem while matching changed view holders with the newones. The pre-layout information for the change holder " + vVar2 + " cannot be found but it is necessary for " + vVar + exceptionLabel());
    }

    /* access modifiers changed from: package-private */
    public void recordAnimationInfoIfBouncedHiddenView(v vVar, f.c cVar) {
        vVar.setFlags(0, 8192);
        if (this.mState.h && vVar.isUpdated() && !vVar.isRemoved() && !vVar.shouldIgnore()) {
            this.mViewInfoStore.a(getChangedHolderKey(vVar), vVar);
        }
        this.mViewInfoStore.a(vVar, cVar);
    }

    private void findMinMaxChildLayoutPositions(int[] iArr) {
        int b2 = this.mChildHelper.b();
        if (b2 == 0) {
            iArr[0] = -1;
            iArr[1] = -1;
            return;
        }
        int i2 = Api.BaseClientBuilder.API_PRIORITY_OTHER;
        int i3 = androidx.customview.a.a.INVALID_ID;
        for (int i4 = 0; i4 < b2; i4++) {
            v childViewHolderInt = getChildViewHolderInt(this.mChildHelper.b(i4));
            if (!childViewHolderInt.shouldIgnore()) {
                int layoutPosition = childViewHolderInt.getLayoutPosition();
                if (layoutPosition < i2) {
                    i2 = layoutPosition;
                }
                if (layoutPosition > i3) {
                    i3 = layoutPosition;
                }
            }
        }
        iArr[0] = i2;
        iArr[1] = i3;
    }

    private boolean didChildRangeChange(int i2, int i3) {
        findMinMaxChildLayoutPositions(this.mMinMaxLayoutPositions);
        int[] iArr = this.mMinMaxLayoutPositions;
        return (iArr[0] == i2 && iArr[1] == i3) ? false : true;
    }

    /* access modifiers changed from: protected */
    public void removeDetachedView(View view, boolean z) {
        v childViewHolderInt = getChildViewHolderInt(view);
        if (childViewHolderInt != null) {
            if (childViewHolderInt.isTmpDetached()) {
                childViewHolderInt.clearTmpDetachFlag();
            } else if (!childViewHolderInt.shouldIgnore()) {
                throw new IllegalArgumentException("Called removeDetachedView with a view which is not flagged as tmp detached." + childViewHolderInt + exceptionLabel());
            }
        }
        view.clearAnimation();
        dispatchChildDetached(view);
        super.removeDetachedView(view, z);
    }

    /* access modifiers changed from: package-private */
    public long getChangedHolderKey(v vVar) {
        return this.mAdapter.hasStableIds() ? vVar.getItemId() : (long) vVar.mPosition;
    }

    /* access modifiers changed from: package-private */
    public void animateAppearance(v vVar, f.c cVar, f.c cVar2) {
        vVar.setIsRecyclable(false);
        if (this.mItemAnimator.b(vVar, cVar, cVar2)) {
            postAnimationRunner();
        }
    }

    /* access modifiers changed from: package-private */
    public void animateDisappearance(v vVar, f.c cVar, f.c cVar2) {
        addAnimatingView(vVar);
        vVar.setIsRecyclable(false);
        if (this.mItemAnimator.a(vVar, cVar, cVar2)) {
            postAnimationRunner();
        }
    }

    private void animateChange(v vVar, v vVar2, f.c cVar, f.c cVar2, boolean z, boolean z2) {
        vVar.setIsRecyclable(false);
        if (z) {
            addAnimatingView(vVar);
        }
        if (vVar != vVar2) {
            if (z2) {
                addAnimatingView(vVar2);
            }
            vVar.mShadowedHolder = vVar2;
            addAnimatingView(vVar);
            this.mRecycler.c(vVar);
            vVar2.setIsRecyclable(false);
            vVar2.mShadowingHolder = vVar;
        }
        if (this.mItemAnimator.a(vVar, vVar2, cVar, cVar2)) {
            postAnimationRunner();
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i2, int i3, int i4, int i5) {
        androidx.core.os.c.a(TRACE_ON_LAYOUT_TAG);
        dispatchLayout();
        androidx.core.os.c.a();
        this.mFirstLayoutComplete = true;
    }

    public void requestLayout() {
        if (this.mInterceptRequestLayoutDepth != 0 || this.mLayoutFrozen) {
            this.mLayoutWasDefered = true;
        } else {
            super.requestLayout();
        }
    }

    /* access modifiers changed from: package-private */
    public void markItemDecorInsetsDirty() {
        int c2 = this.mChildHelper.c();
        for (int i2 = 0; i2 < c2; i2++) {
            ((LayoutParams) this.mChildHelper.d(i2).getLayoutParams()).e = true;
        }
        this.mRecycler.j();
    }

    public void draw(Canvas canvas) {
        boolean z;
        boolean z2;
        super.draw(canvas);
        int size = this.mItemDecorations.size();
        boolean z3 = false;
        for (int i2 = 0; i2 < size; i2++) {
            this.mItemDecorations.get(i2).a(canvas, this, this.mState);
        }
        EdgeEffect edgeEffect = this.mLeftGlow;
        if (edgeEffect == null || edgeEffect.isFinished()) {
            z = false;
        } else {
            int save = canvas.save();
            int paddingBottom = this.mClipToPadding ? getPaddingBottom() : 0;
            canvas.rotate(270.0f);
            canvas.translate((float) ((-getHeight()) + paddingBottom), 0.0f);
            EdgeEffect edgeEffect2 = this.mLeftGlow;
            z = edgeEffect2 != null && edgeEffect2.draw(canvas);
            canvas.restoreToCount(save);
        }
        EdgeEffect edgeEffect3 = this.mTopGlow;
        if (edgeEffect3 != null && !edgeEffect3.isFinished()) {
            int save2 = canvas.save();
            if (this.mClipToPadding) {
                canvas.translate((float) getPaddingLeft(), (float) getPaddingTop());
            }
            EdgeEffect edgeEffect4 = this.mTopGlow;
            z |= edgeEffect4 != null && edgeEffect4.draw(canvas);
            canvas.restoreToCount(save2);
        }
        EdgeEffect edgeEffect5 = this.mRightGlow;
        if (edgeEffect5 != null && !edgeEffect5.isFinished()) {
            int save3 = canvas.save();
            int width = getWidth();
            int paddingTop = this.mClipToPadding ? getPaddingTop() : 0;
            canvas.rotate(90.0f);
            canvas.translate((float) (-paddingTop), (float) (-width));
            EdgeEffect edgeEffect6 = this.mRightGlow;
            z |= edgeEffect6 != null && edgeEffect6.draw(canvas);
            canvas.restoreToCount(save3);
        }
        EdgeEffect edgeEffect7 = this.mBottomGlow;
        if (edgeEffect7 == null || edgeEffect7.isFinished()) {
            z2 = z;
        } else {
            int save4 = canvas.save();
            canvas.rotate(180.0f);
            if (this.mClipToPadding) {
                canvas.translate((float) ((-getWidth()) + getPaddingRight()), (float) ((-getHeight()) + getPaddingBottom()));
            } else {
                canvas.translate((float) (-getWidth()), (float) (-getHeight()));
            }
            EdgeEffect edgeEffect8 = this.mBottomGlow;
            if (edgeEffect8 != null && edgeEffect8.draw(canvas)) {
                z3 = true;
            }
            z2 = z3 | z;
            canvas.restoreToCount(save4);
        }
        if (!z2 && this.mItemAnimator != null && this.mItemDecorations.size() > 0 && this.mItemAnimator.b()) {
            z2 = true;
        }
        if (z2) {
            androidx.core.f.r.d(this);
        }
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int size = this.mItemDecorations.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.mItemDecorations.get(i2).b(canvas, this, this.mState);
        }
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof LayoutParams) && this.mLayout.a((LayoutParams) layoutParams);
    }

    /* access modifiers changed from: protected */
    public ViewGroup.LayoutParams generateDefaultLayoutParams() {
        i iVar = this.mLayout;
        if (iVar != null) {
            return iVar.a();
        }
        throw new IllegalStateException("RecyclerView has no LayoutManager" + exceptionLabel());
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        i iVar = this.mLayout;
        if (iVar != null) {
            return iVar.a(getContext(), attributeSet);
        }
        throw new IllegalStateException("RecyclerView has no LayoutManager" + exceptionLabel());
    }

    /* access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        i iVar = this.mLayout;
        if (iVar != null) {
            return iVar.a(layoutParams);
        }
        throw new IllegalStateException("RecyclerView has no LayoutManager" + exceptionLabel());
    }

    public boolean isAnimating() {
        f fVar = this.mItemAnimator;
        return fVar != null && fVar.b();
    }

    /* access modifiers changed from: package-private */
    public void saveOldPositions() {
        int c2 = this.mChildHelper.c();
        for (int i2 = 0; i2 < c2; i2++) {
            v childViewHolderInt = getChildViewHolderInt(this.mChildHelper.d(i2));
            if (!childViewHolderInt.shouldIgnore()) {
                childViewHolderInt.saveOldPosition();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void clearOldPositions() {
        int c2 = this.mChildHelper.c();
        for (int i2 = 0; i2 < c2; i2++) {
            v childViewHolderInt = getChildViewHolderInt(this.mChildHelper.d(i2));
            if (!childViewHolderInt.shouldIgnore()) {
                childViewHolderInt.clearOldPosition();
            }
        }
        this.mRecycler.i();
    }

    /* access modifiers changed from: package-private */
    public void offsetPositionRecordsForMove(int i2, int i3) {
        int i4;
        int i5;
        int i6;
        int c2 = this.mChildHelper.c();
        if (i2 < i3) {
            i6 = i2;
            i5 = i3;
            i4 = -1;
        } else {
            i5 = i2;
            i6 = i3;
            i4 = 1;
        }
        for (int i7 = 0; i7 < c2; i7++) {
            v childViewHolderInt = getChildViewHolderInt(this.mChildHelper.d(i7));
            if (childViewHolderInt != null && childViewHolderInt.mPosition >= i6 && childViewHolderInt.mPosition <= i5) {
                if (childViewHolderInt.mPosition == i2) {
                    childViewHolderInt.offsetPosition(i3 - i2, false);
                } else {
                    childViewHolderInt.offsetPosition(i4, false);
                }
                this.mState.f = true;
            }
        }
        this.mRecycler.a(i2, i3);
        requestLayout();
    }

    /* access modifiers changed from: package-private */
    public void offsetPositionRecordsForInsert(int i2, int i3) {
        int c2 = this.mChildHelper.c();
        for (int i4 = 0; i4 < c2; i4++) {
            v childViewHolderInt = getChildViewHolderInt(this.mChildHelper.d(i4));
            if (childViewHolderInt != null && !childViewHolderInt.shouldIgnore() && childViewHolderInt.mPosition >= i2) {
                childViewHolderInt.offsetPosition(i3, false);
                this.mState.f = true;
            }
        }
        this.mRecycler.b(i2, i3);
        requestLayout();
    }

    /* access modifiers changed from: package-private */
    public void offsetPositionRecordsForRemove(int i2, int i3, boolean z) {
        int i4 = i2 + i3;
        int c2 = this.mChildHelper.c();
        for (int i5 = 0; i5 < c2; i5++) {
            v childViewHolderInt = getChildViewHolderInt(this.mChildHelper.d(i5));
            if (childViewHolderInt != null && !childViewHolderInt.shouldIgnore()) {
                if (childViewHolderInt.mPosition >= i4) {
                    childViewHolderInt.offsetPosition(-i3, z);
                    this.mState.f = true;
                } else if (childViewHolderInt.mPosition >= i2) {
                    childViewHolderInt.flagRemovedAndOffsetPosition(i2 - 1, -i3, z);
                    this.mState.f = true;
                }
            }
        }
        this.mRecycler.a(i2, i3, z);
        requestLayout();
    }

    /* access modifiers changed from: package-private */
    public void viewRangeUpdate(int i2, int i3, Object obj) {
        int c2 = this.mChildHelper.c();
        int i4 = i2 + i3;
        for (int i5 = 0; i5 < c2; i5++) {
            View d2 = this.mChildHelper.d(i5);
            v childViewHolderInt = getChildViewHolderInt(d2);
            if (childViewHolderInt != null && !childViewHolderInt.shouldIgnore() && childViewHolderInt.mPosition >= i2 && childViewHolderInt.mPosition < i4) {
                childViewHolderInt.addFlags(2);
                childViewHolderInt.addChangePayload(obj);
                ((LayoutParams) d2.getLayoutParams()).e = true;
            }
        }
        this.mRecycler.c(i2, i3);
    }

    /* access modifiers changed from: package-private */
    public boolean canReuseUpdatedViewHolder(v vVar) {
        f fVar = this.mItemAnimator;
        return fVar == null || fVar.a(vVar, vVar.getUnmodifiedPayloads());
    }

    /* access modifiers changed from: package-private */
    public void processDataSetCompletelyChanged(boolean z) {
        this.mDispatchItemsChangedEvent = z | this.mDispatchItemsChangedEvent;
        this.mDataSetHasChangedAfterLayout = true;
        markKnownViewsInvalid();
    }

    /* access modifiers changed from: package-private */
    public void markKnownViewsInvalid() {
        int c2 = this.mChildHelper.c();
        for (int i2 = 0; i2 < c2; i2++) {
            v childViewHolderInt = getChildViewHolderInt(this.mChildHelper.d(i2));
            if (childViewHolderInt != null && !childViewHolderInt.shouldIgnore()) {
                childViewHolderInt.addFlags(6);
            }
        }
        markItemDecorInsetsDirty();
        this.mRecycler.h();
    }

    public void invalidateItemDecorations() {
        if (this.mItemDecorations.size() != 0) {
            i iVar = this.mLayout;
            if (iVar != null) {
                iVar.a("Cannot invalidate item decorations during a scroll or layout");
            }
            markItemDecorInsetsDirty();
            requestLayout();
        }
    }

    public boolean getPreserveFocusAfterLayout() {
        return this.mPreserveFocusAfterLayout;
    }

    public void setPreserveFocusAfterLayout(boolean z) {
        this.mPreserveFocusAfterLayout = z;
    }

    public v getChildViewHolder(View view) {
        ViewParent parent = view.getParent();
        if (parent == null || parent == this) {
            return getChildViewHolderInt(view);
        }
        throw new IllegalArgumentException("View " + view + " is not a direct child of " + this);
    }

    public View findContainingItemView(View view) {
        ViewParent parent = view.getParent();
        while (parent != null && parent != this && (parent instanceof View)) {
            view = (View) parent;
            parent = view.getParent();
        }
        if (parent == this) {
            return view;
        }
        return null;
    }

    public v findContainingViewHolder(View view) {
        View findContainingItemView = findContainingItemView(view);
        if (findContainingItemView == null) {
            return null;
        }
        return getChildViewHolder(findContainingItemView);
    }

    static v getChildViewHolderInt(View view) {
        if (view == null) {
            return null;
        }
        return ((LayoutParams) view.getLayoutParams()).c;
    }

    @Deprecated
    public int getChildPosition(View view) {
        return getChildAdapterPosition(view);
    }

    public int getChildAdapterPosition(View view) {
        v childViewHolderInt = getChildViewHolderInt(view);
        if (childViewHolderInt != null) {
            return childViewHolderInt.getAdapterPosition();
        }
        return -1;
    }

    public int getChildLayoutPosition(View view) {
        v childViewHolderInt = getChildViewHolderInt(view);
        if (childViewHolderInt != null) {
            return childViewHolderInt.getLayoutPosition();
        }
        return -1;
    }

    public long getChildItemId(View view) {
        v childViewHolderInt;
        a aVar = this.mAdapter;
        if (aVar == null || !aVar.hasStableIds() || (childViewHolderInt = getChildViewHolderInt(view)) == null) {
            return -1;
        }
        return childViewHolderInt.getItemId();
    }

    @Deprecated
    public v findViewHolderForPosition(int i2) {
        return findViewHolderForPosition(i2, false);
    }

    public v findViewHolderForLayoutPosition(int i2) {
        return findViewHolderForPosition(i2, false);
    }

    public v findViewHolderForAdapterPosition(int i2) {
        v vVar = null;
        if (this.mDataSetHasChangedAfterLayout) {
            return null;
        }
        int c2 = this.mChildHelper.c();
        for (int i3 = 0; i3 < c2; i3++) {
            v childViewHolderInt = getChildViewHolderInt(this.mChildHelper.d(i3));
            if (childViewHolderInt != null && !childViewHolderInt.isRemoved() && getAdapterPositionFor(childViewHolderInt) == i2) {
                if (!this.mChildHelper.c(childViewHolderInt.itemView)) {
                    return childViewHolderInt;
                }
                vVar = childViewHolderInt;
            }
        }
        return vVar;
    }

    /* access modifiers changed from: package-private */
    public v findViewHolderForPosition(int i2, boolean z) {
        int c2 = this.mChildHelper.c();
        v vVar = null;
        for (int i3 = 0; i3 < c2; i3++) {
            v childViewHolderInt = getChildViewHolderInt(this.mChildHelper.d(i3));
            if (childViewHolderInt != null && !childViewHolderInt.isRemoved()) {
                if (z) {
                    if (childViewHolderInt.mPosition != i2) {
                        continue;
                    }
                } else if (childViewHolderInt.getLayoutPosition() != i2) {
                    continue;
                }
                if (!this.mChildHelper.c(childViewHolderInt.itemView)) {
                    return childViewHolderInt;
                }
                vVar = childViewHolderInt;
            }
        }
        return vVar;
    }

    public v findViewHolderForItemId(long j2) {
        a aVar = this.mAdapter;
        v vVar = null;
        if (aVar == null || !aVar.hasStableIds()) {
            return null;
        }
        int c2 = this.mChildHelper.c();
        for (int i2 = 0; i2 < c2; i2++) {
            v childViewHolderInt = getChildViewHolderInt(this.mChildHelper.d(i2));
            if (childViewHolderInt != null && !childViewHolderInt.isRemoved() && childViewHolderInt.getItemId() == j2) {
                if (!this.mChildHelper.c(childViewHolderInt.itemView)) {
                    return childViewHolderInt;
                }
                vVar = childViewHolderInt;
            }
        }
        return vVar;
    }

    public View findChildViewUnder(float f2, float f3) {
        for (int b2 = this.mChildHelper.b() - 1; b2 >= 0; b2--) {
            View b3 = this.mChildHelper.b(b2);
            float translationX = b3.getTranslationX();
            float translationY = b3.getTranslationY();
            if (f2 >= ((float) b3.getLeft()) + translationX && f2 <= ((float) b3.getRight()) + translationX && f3 >= ((float) b3.getTop()) + translationY && f3 <= ((float) b3.getBottom()) + translationY) {
                return b3;
            }
        }
        return null;
    }

    public boolean drawChild(Canvas canvas, View view, long j2) {
        return super.drawChild(canvas, view, j2);
    }

    public void offsetChildrenVertical(int i2) {
        int b2 = this.mChildHelper.b();
        for (int i3 = 0; i3 < b2; i3++) {
            this.mChildHelper.b(i3).offsetTopAndBottom(i2);
        }
    }

    public void offsetChildrenHorizontal(int i2) {
        int b2 = this.mChildHelper.b();
        for (int i3 = 0; i3 < b2; i3++) {
            this.mChildHelper.b(i3).offsetLeftAndRight(i2);
        }
    }

    public void getDecoratedBoundsWithMargins(View view, Rect rect) {
        getDecoratedBoundsWithMarginsInt(view, rect);
    }

    static void getDecoratedBoundsWithMarginsInt(View view, Rect rect) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        Rect rect2 = layoutParams.d;
        rect.set((view.getLeft() - rect2.left) - layoutParams.leftMargin, (view.getTop() - rect2.top) - layoutParams.topMargin, view.getRight() + rect2.right + layoutParams.rightMargin, view.getBottom() + rect2.bottom + layoutParams.bottomMargin);
    }

    /* access modifiers changed from: package-private */
    public Rect getItemDecorInsetsForChild(View view) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (!layoutParams.e) {
            return layoutParams.d;
        }
        if (this.mState.a() && (layoutParams.e() || layoutParams.c())) {
            return layoutParams.d;
        }
        Rect rect = layoutParams.d;
        rect.set(0, 0, 0, 0);
        int size = this.mItemDecorations.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.mTempRect.set(0, 0, 0, 0);
            this.mItemDecorations.get(i2).a(this.mTempRect, view, this, this.mState);
            rect.left += this.mTempRect.left;
            rect.top += this.mTempRect.top;
            rect.right += this.mTempRect.right;
            rect.bottom += this.mTempRect.bottom;
        }
        layoutParams.e = false;
        return rect;
    }

    /* access modifiers changed from: package-private */
    public void dispatchOnScrolled(int i2, int i3) {
        this.mDispatchScrollCounter++;
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        onScrollChanged(scrollX, scrollY, scrollX, scrollY);
        onScrolled(i2, i3);
        m mVar = this.mScrollListener;
        if (mVar != null) {
            mVar.a(this, i2, i3);
        }
        List<m> list = this.mScrollListeners;
        if (list != null) {
            for (int size = list.size() - 1; size >= 0; size--) {
                this.mScrollListeners.get(size).a(this, i2, i3);
            }
        }
        this.mDispatchScrollCounter--;
    }

    /* access modifiers changed from: package-private */
    public void dispatchOnScrollStateChanged(int i2) {
        i iVar = this.mLayout;
        if (iVar != null) {
            iVar.l(i2);
        }
        onScrollStateChanged(i2);
        m mVar = this.mScrollListener;
        if (mVar != null) {
            mVar.a(this, i2);
        }
        List<m> list = this.mScrollListeners;
        if (list != null) {
            for (int size = list.size() - 1; size >= 0; size--) {
                this.mScrollListeners.get(size).a(this, i2);
            }
        }
    }

    public boolean hasPendingAdapterUpdates() {
        return !this.mFirstLayoutComplete || this.mDataSetHasChangedAfterLayout || this.mAdapterHelper.d();
    }

    /* access modifiers changed from: package-private */
    public class u implements Runnable {
        OverScroller a;
        Interpolator b = RecyclerView.sQuinticInterpolator;
        private int d;
        private int e;
        private boolean f = false;
        private boolean g = false;

        u() {
            this.a = new OverScroller(RecyclerView.this.getContext(), RecyclerView.sQuinticInterpolator);
        }

        public void run() {
            int i;
            int i2;
            int i3;
            int i4;
            if (RecyclerView.this.mLayout == null) {
                b();
                return;
            }
            c();
            RecyclerView.this.consumePendingUpdateOperations();
            OverScroller overScroller = this.a;
            r rVar = RecyclerView.this.mLayout.t;
            if (overScroller.computeScrollOffset()) {
                int[] iArr = RecyclerView.this.mScrollConsumed;
                int currX = overScroller.getCurrX();
                int currY = overScroller.getCurrY();
                int i5 = currX - this.d;
                int i6 = currY - this.e;
                this.d = currX;
                this.e = currY;
                if (RecyclerView.this.dispatchNestedPreScroll(i5, i6, iArr, null, 1)) {
                    i5 -= iArr[0];
                    i6 -= iArr[1];
                }
                if (RecyclerView.this.mAdapter != null) {
                    RecyclerView recyclerView = RecyclerView.this;
                    recyclerView.scrollStep(i5, i6, recyclerView.mScrollStepConsumed);
                    i4 = RecyclerView.this.mScrollStepConsumed[0];
                    i3 = RecyclerView.this.mScrollStepConsumed[1];
                    i2 = i5 - i4;
                    i = i6 - i3;
                    if (rVar != null && !rVar.g() && rVar.h()) {
                        int e2 = RecyclerView.this.mState.e();
                        if (e2 == 0) {
                            rVar.f();
                        } else if (rVar.i() >= e2) {
                            rVar.c(e2 - 1);
                            rVar.a(i5 - i2, i6 - i);
                        } else {
                            rVar.a(i5 - i2, i6 - i);
                        }
                    }
                } else {
                    i4 = 0;
                    i3 = 0;
                    i2 = 0;
                    i = 0;
                }
                if (!RecyclerView.this.mItemDecorations.isEmpty()) {
                    RecyclerView.this.invalidate();
                }
                if (RecyclerView.this.getOverScrollMode() != 2) {
                    RecyclerView.this.considerReleasingGlowsOnScroll(i5, i6);
                }
                if (!RecyclerView.this.dispatchNestedScroll(i4, i3, i2, i, null, 1) && !(i2 == 0 && i == 0)) {
                    int currVelocity = (int) overScroller.getCurrVelocity();
                    int i7 = i2 != currX ? i2 < 0 ? -currVelocity : i2 > 0 ? currVelocity : 0 : 0;
                    if (i == currY) {
                        currVelocity = 0;
                    } else if (i < 0) {
                        currVelocity = -currVelocity;
                    } else if (i <= 0) {
                        currVelocity = 0;
                    }
                    if (RecyclerView.this.getOverScrollMode() != 2) {
                        RecyclerView.this.absorbGlows(i7, currVelocity);
                    }
                    if ((i7 != 0 || i2 == currX || overScroller.getFinalX() == 0) && (currVelocity != 0 || i == currY || overScroller.getFinalY() == 0)) {
                        overScroller.abortAnimation();
                    }
                }
                if (!(i4 == 0 && i3 == 0)) {
                    RecyclerView.this.dispatchOnScrolled(i4, i3);
                }
                if (!RecyclerView.this.awakenScrollBars()) {
                    RecyclerView.this.invalidate();
                }
                boolean z = (i5 == 0 && i6 == 0) || (i5 != 0 && RecyclerView.this.mLayout.e() && i4 == i5) || (i6 != 0 && RecyclerView.this.mLayout.f() && i3 == i6);
                if (overScroller.isFinished() || (!z && !RecyclerView.this.hasNestedScrollingParent(1))) {
                    RecyclerView.this.setScrollState(0);
                    if (RecyclerView.ALLOW_THREAD_GAP_WORK) {
                        RecyclerView.this.mPrefetchRegistry.a();
                    }
                    RecyclerView.this.stopNestedScroll(1);
                } else {
                    a();
                    if (RecyclerView.this.mGapWorker != null) {
                        RecyclerView.this.mGapWorker.a(RecyclerView.this, i5, i6);
                    }
                }
            }
            if (rVar != null) {
                if (rVar.g()) {
                    rVar.a(0, 0);
                }
                if (!this.g) {
                    rVar.f();
                }
            }
            d();
        }

        private void c() {
            this.g = false;
            this.f = true;
        }

        private void d() {
            this.f = false;
            if (this.g) {
                a();
            }
        }

        /* access modifiers changed from: package-private */
        public void a() {
            if (this.f) {
                this.g = true;
                return;
            }
            RecyclerView.this.removeCallbacks(this);
            androidx.core.f.r.a(RecyclerView.this, this);
        }

        public void a(int i, int i2) {
            RecyclerView.this.setScrollState(2);
            this.e = 0;
            this.d = 0;
            this.a.fling(0, 0, i, i2, androidx.customview.a.a.INVALID_ID, Api.BaseClientBuilder.API_PRIORITY_OTHER, androidx.customview.a.a.INVALID_ID, Api.BaseClientBuilder.API_PRIORITY_OTHER);
            a();
        }

        public void b(int i, int i2) {
            a(i, i2, 0, 0);
        }

        public void a(int i, int i2, int i3, int i4) {
            a(i, i2, b(i, i2, i3, i4));
        }

        private float a(float f2) {
            return (float) Math.sin((double) ((f2 - 0.5f) * 0.47123894f));
        }

        private int b(int i, int i2, int i3, int i4) {
            int i5;
            int abs = Math.abs(i);
            int abs2 = Math.abs(i2);
            boolean z = abs > abs2;
            int sqrt = (int) Math.sqrt((double) ((i3 * i3) + (i4 * i4)));
            int sqrt2 = (int) Math.sqrt((double) ((i * i) + (i2 * i2)));
            int width = z ? RecyclerView.this.getWidth() : RecyclerView.this.getHeight();
            int i6 = width / 2;
            float f2 = (float) width;
            float f3 = (float) i6;
            float a2 = f3 + (a(Math.min(1.0f, (((float) sqrt2) * 1.0f) / f2)) * f3);
            if (sqrt > 0) {
                i5 = Math.round(Math.abs(a2 / ((float) sqrt)) * 1000.0f) * 4;
            } else {
                if (!z) {
                    abs = abs2;
                }
                i5 = (int) (((((float) abs) / f2) + 1.0f) * 300.0f);
            }
            return Math.min(i5, 2000);
        }

        public void a(int i, int i2, int i3) {
            a(i, i2, i3, RecyclerView.sQuinticInterpolator);
        }

        public void a(int i, int i2, Interpolator interpolator) {
            int b2 = b(i, i2, 0, 0);
            if (interpolator == null) {
                interpolator = RecyclerView.sQuinticInterpolator;
            }
            a(i, i2, b2, interpolator);
        }

        public void a(int i, int i2, int i3, Interpolator interpolator) {
            if (this.b != interpolator) {
                this.b = interpolator;
                this.a = new OverScroller(RecyclerView.this.getContext(), interpolator);
            }
            RecyclerView.this.setScrollState(2);
            this.e = 0;
            this.d = 0;
            this.a.startScroll(0, 0, i, i2, i3);
            if (Build.VERSION.SDK_INT < 23) {
                this.a.computeScrollOffset();
            }
            a();
        }

        public void b() {
            RecyclerView.this.removeCallbacks(this);
            this.a.abortAnimation();
        }
    }

    /* access modifiers changed from: package-private */
    public void repositionShadowingViews() {
        int b2 = this.mChildHelper.b();
        for (int i2 = 0; i2 < b2; i2++) {
            View b3 = this.mChildHelper.b(i2);
            v childViewHolder = getChildViewHolder(b3);
            if (!(childViewHolder == null || childViewHolder.mShadowingHolder == null)) {
                View view = childViewHolder.mShadowingHolder.itemView;
                int left = b3.getLeft();
                int top = b3.getTop();
                if (left != view.getLeft() || top != view.getTop()) {
                    view.layout(left, top, view.getWidth() + left, view.getHeight() + top);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public class q extends c {
        q() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.c
        public void a() {
            RecyclerView.this.assertNotInLayoutOrScroll(null);
            RecyclerView.this.mState.f = true;
            RecyclerView.this.processDataSetCompletelyChanged(true);
            if (!RecyclerView.this.mAdapterHelper.d()) {
                RecyclerView.this.requestLayout();
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.c
        public void a(int i, int i2, Object obj) {
            RecyclerView.this.assertNotInLayoutOrScroll(null);
            if (RecyclerView.this.mAdapterHelper.a(i, i2, obj)) {
                b();
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.c
        public void b(int i, int i2) {
            RecyclerView.this.assertNotInLayoutOrScroll(null);
            if (RecyclerView.this.mAdapterHelper.b(i, i2)) {
                b();
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.c
        public void c(int i, int i2) {
            RecyclerView.this.assertNotInLayoutOrScroll(null);
            if (RecyclerView.this.mAdapterHelper.c(i, i2)) {
                b();
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.c
        public void a(int i, int i2, int i3) {
            RecyclerView.this.assertNotInLayoutOrScroll(null);
            if (RecyclerView.this.mAdapterHelper.a(i, i2, i3)) {
                b();
            }
        }

        /* access modifiers changed from: package-private */
        public void b() {
            if (!RecyclerView.POST_UPDATES_ON_ANIMATION || !RecyclerView.this.mHasFixedSize || !RecyclerView.this.mIsAttached) {
                RecyclerView recyclerView = RecyclerView.this;
                recyclerView.mAdapterUpdateDuringMeasure = true;
                recyclerView.requestLayout();
                return;
            }
            RecyclerView recyclerView2 = RecyclerView.this;
            androidx.core.f.r.a(recyclerView2, recyclerView2.mUpdateChildViewsRunnable);
        }
    }

    public static class e {
        /* access modifiers changed from: protected */
        public EdgeEffect a(RecyclerView recyclerView, int i) {
            return new EdgeEffect(recyclerView.getContext());
        }
    }

    public static class n {
        SparseArray<a> a = new SparseArray<>();
        private int b = 0;

        /* access modifiers changed from: package-private */
        public static class a {
            final ArrayList<v> a = new ArrayList<>();
            int b = 5;
            long c = 0;
            long d = 0;

            a() {
            }
        }

        public void a() {
            for (int i = 0; i < this.a.size(); i++) {
                this.a.valueAt(i).a.clear();
            }
        }

        public v a(int i) {
            a aVar = this.a.get(i);
            if (aVar == null || aVar.a.isEmpty()) {
                return null;
            }
            ArrayList<v> arrayList = aVar.a;
            return arrayList.remove(arrayList.size() - 1);
        }

        public void a(v vVar) {
            int itemViewType = vVar.getItemViewType();
            ArrayList<v> arrayList = b(itemViewType).a;
            if (this.a.get(itemViewType).b > arrayList.size()) {
                vVar.resetInternal();
                arrayList.add(vVar);
            }
        }

        /* access modifiers changed from: package-private */
        public long a(long j, long j2) {
            return j == 0 ? j2 : ((j / 4) * 3) + (j2 / 4);
        }

        /* access modifiers changed from: package-private */
        public void a(int i, long j) {
            a b2 = b(i);
            b2.c = a(b2.c, j);
        }

        /* access modifiers changed from: package-private */
        public void b(int i, long j) {
            a b2 = b(i);
            b2.d = a(b2.d, j);
        }

        /* access modifiers changed from: package-private */
        public boolean a(int i, long j, long j2) {
            long j3 = b(i).c;
            return j3 == 0 || j + j3 < j2;
        }

        /* access modifiers changed from: package-private */
        public boolean b(int i, long j, long j2) {
            long j3 = b(i).d;
            return j3 == 0 || j + j3 < j2;
        }

        /* access modifiers changed from: package-private */
        public void b() {
            this.b++;
        }

        /* access modifiers changed from: package-private */
        public void c() {
            this.b--;
        }

        /* access modifiers changed from: package-private */
        public void a(a aVar, a aVar2, boolean z) {
            if (aVar != null) {
                c();
            }
            if (!z && this.b == 0) {
                a();
            }
            if (aVar2 != null) {
                b();
            }
        }

        private a b(int i) {
            a aVar = this.a.get(i);
            if (aVar != null) {
                return aVar;
            }
            a aVar2 = new a();
            this.a.put(i, aVar2);
            return aVar2;
        }
    }

    static RecyclerView findNestedRecyclerView(View view) {
        if (!(view instanceof ViewGroup)) {
            return null;
        }
        if (view instanceof RecyclerView) {
            return (RecyclerView) view;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        int childCount = viewGroup.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            RecyclerView findNestedRecyclerView = findNestedRecyclerView(viewGroup.getChildAt(i2));
            if (findNestedRecyclerView != null) {
                return findNestedRecyclerView;
            }
        }
        return null;
    }

    static void clearNestedRecyclerViewIfNotNested(v vVar) {
        if (vVar.mNestedRecyclerView != null) {
            RecyclerView recyclerView = vVar.mNestedRecyclerView.get();
            while (recyclerView != null) {
                if (recyclerView != vVar.itemView) {
                    ViewParent parent = recyclerView.getParent();
                    recyclerView = parent instanceof View ? (View) parent : null;
                } else {
                    return;
                }
            }
            vVar.mNestedRecyclerView = null;
        }
    }

    /* access modifiers changed from: package-private */
    public long getNanoTime() {
        if (ALLOW_THREAD_GAP_WORK) {
            return System.nanoTime();
        }
        return 0;
    }

    public final class o {
        final ArrayList<v> a = new ArrayList<>();
        ArrayList<v> b = null;
        final ArrayList<v> c = new ArrayList<>();
        int d = 2;
        n e;
        private final List<v> g = Collections.unmodifiableList(this.a);
        private int h = 2;
        private t i;

        public o() {
        }

        public void a() {
            this.a.clear();
            d();
        }

        public void a(int i2) {
            this.h = i2;
            b();
        }

        /* access modifiers changed from: package-private */
        public void b() {
            this.d = this.h + (RecyclerView.this.mLayout != null ? RecyclerView.this.mLayout.x : 0);
            for (int size = this.c.size() - 1; size >= 0 && this.c.size() > this.d; size--) {
                d(size);
            }
        }

        public List<v> c() {
            return this.g;
        }

        /* access modifiers changed from: package-private */
        public boolean a(v vVar) {
            if (vVar.isRemoved()) {
                return RecyclerView.this.mState.a();
            }
            if (vVar.mPosition < 0 || vVar.mPosition >= RecyclerView.this.mAdapter.getItemCount()) {
                throw new IndexOutOfBoundsException("Inconsistency detected. Invalid view holder adapter position" + vVar + RecyclerView.this.exceptionLabel());
            } else if (!RecyclerView.this.mState.a() && RecyclerView.this.mAdapter.getItemViewType(vVar.mPosition) != vVar.getItemViewType()) {
                return false;
            } else {
                if (!RecyclerView.this.mAdapter.hasStableIds() || vVar.getItemId() == RecyclerView.this.mAdapter.getItemId(vVar.mPosition)) {
                    return true;
                }
                return false;
            }
        }

        private boolean a(v vVar, int i2, int i3, long j) {
            vVar.mOwnerRecyclerView = RecyclerView.this;
            int itemViewType = vVar.getItemViewType();
            long nanoTime = RecyclerView.this.getNanoTime();
            if (j != RecyclerView.FOREVER_NS && !this.e.b(itemViewType, nanoTime, j)) {
                return false;
            }
            RecyclerView.this.mAdapter.bindViewHolder(vVar, i2);
            this.e.b(vVar.getItemViewType(), RecyclerView.this.getNanoTime() - nanoTime);
            e(vVar);
            if (!RecyclerView.this.mState.a()) {
                return true;
            }
            vVar.mPreLayoutPosition = i3;
            return true;
        }

        public int b(int i2) {
            if (i2 < 0 || i2 >= RecyclerView.this.mState.e()) {
                throw new IndexOutOfBoundsException("invalid position " + i2 + ". State " + "item count is " + RecyclerView.this.mState.e() + RecyclerView.this.exceptionLabel());
            } else if (!RecyclerView.this.mState.a()) {
                return i2;
            } else {
                return RecyclerView.this.mAdapterHelper.b(i2);
            }
        }

        public View c(int i2) {
            return a(i2, false);
        }

        /* access modifiers changed from: package-private */
        public View a(int i2, boolean z) {
            return a(i2, z, RecyclerView.FOREVER_NS).itemView;
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Removed duplicated region for block: B:94:0x020c  */
        /* JADX WARNING: Removed duplicated region for block: B:95:0x021a  */
        public v a(int i2, boolean z, long j) {
            boolean z2;
            v vVar;
            v vVar2;
            boolean z3;
            boolean z4;
            ViewGroup.LayoutParams layoutParams;
            LayoutParams layoutParams2;
            RecyclerView findNestedRecyclerView;
            t tVar;
            View a2;
            if (i2 < 0 || i2 >= RecyclerView.this.mState.e()) {
                throw new IndexOutOfBoundsException("Invalid item position " + i2 + "(" + i2 + "). Item count:" + RecyclerView.this.mState.e() + RecyclerView.this.exceptionLabel());
            }
            boolean z5 = true;
            if (RecyclerView.this.mState.a()) {
                vVar = f(i2);
                z2 = vVar != null;
            } else {
                vVar = null;
                z2 = false;
            }
            if (vVar == null && (vVar = b(i2, z)) != null) {
                if (!a(vVar)) {
                    if (!z) {
                        vVar.addFlags(4);
                        if (vVar.isScrap()) {
                            RecyclerView.this.removeDetachedView(vVar.itemView, false);
                            vVar.unScrap();
                        } else if (vVar.wasReturnedFromScrap()) {
                            vVar.clearReturnedFromScrapFlag();
                        }
                        b(vVar);
                    }
                    vVar = null;
                } else {
                    z2 = true;
                }
            }
            if (vVar == null) {
                int b2 = RecyclerView.this.mAdapterHelper.b(i2);
                if (b2 < 0 || b2 >= RecyclerView.this.mAdapter.getItemCount()) {
                    throw new IndexOutOfBoundsException("Inconsistency detected. Invalid item position " + i2 + "(offset:" + b2 + ")." + "state:" + RecyclerView.this.mState.e() + RecyclerView.this.exceptionLabel());
                }
                int itemViewType = RecyclerView.this.mAdapter.getItemViewType(b2);
                if (RecyclerView.this.mAdapter.hasStableIds() && (vVar = a(RecyclerView.this.mAdapter.getItemId(b2), itemViewType, z)) != null) {
                    vVar.mPosition = b2;
                    z2 = true;
                }
                if (!(vVar != null || (tVar = this.i) == null || (a2 = tVar.a(this, i2, itemViewType)) == null)) {
                    vVar = RecyclerView.this.getChildViewHolder(a2);
                    if (vVar == null) {
                        throw new IllegalArgumentException("getViewForPositionAndType returned a view which does not have a ViewHolder" + RecyclerView.this.exceptionLabel());
                    } else if (vVar.shouldIgnore()) {
                        throw new IllegalArgumentException("getViewForPositionAndType returned a view that is ignored. You must call stopIgnoring before returning this view." + RecyclerView.this.exceptionLabel());
                    }
                }
                if (vVar == null && (vVar = g().a(itemViewType)) != null) {
                    vVar.resetInternal();
                    if (RecyclerView.FORCE_INVALIDATE_DISPLAY_LIST) {
                        f(vVar);
                    }
                }
                if (vVar == null) {
                    long nanoTime = RecyclerView.this.getNanoTime();
                    if (j != RecyclerView.FOREVER_NS && !this.e.a(itemViewType, nanoTime, j)) {
                        return null;
                    }
                    v createViewHolder = RecyclerView.this.mAdapter.createViewHolder(RecyclerView.this, itemViewType);
                    if (RecyclerView.ALLOW_THREAD_GAP_WORK && (findNestedRecyclerView = RecyclerView.findNestedRecyclerView(createViewHolder.itemView)) != null) {
                        createViewHolder.mNestedRecyclerView = new WeakReference<>(findNestedRecyclerView);
                    }
                    this.e.a(itemViewType, RecyclerView.this.getNanoTime() - nanoTime);
                    vVar2 = createViewHolder;
                    z3 = z2;
                } else {
                    vVar2 = vVar;
                    z3 = z2;
                }
            } else {
                vVar2 = vVar;
                z3 = z2;
            }
            if (z3 && !RecyclerView.this.mState.a() && vVar2.hasAnyOfTheFlags(8192)) {
                vVar2.setFlags(0, 8192);
                if (RecyclerView.this.mState.j) {
                    RecyclerView.this.recordAnimationInfoIfBouncedHiddenView(vVar2, RecyclerView.this.mItemAnimator.a(RecyclerView.this.mState, vVar2, f.e(vVar2) | TasDefs.ADDITIONAL_DATA_MAX_LENGTH, vVar2.getUnmodifiedPayloads()));
                }
            }
            if (RecyclerView.this.mState.a() && vVar2.isBound()) {
                vVar2.mPreLayoutPosition = i2;
            } else if (!vVar2.isBound() || vVar2.needsUpdate() || vVar2.isInvalid()) {
                z4 = a(vVar2, RecyclerView.this.mAdapterHelper.b(i2), i2, j);
                layoutParams = vVar2.itemView.getLayoutParams();
                if (layoutParams != null) {
                    layoutParams2 = (LayoutParams) RecyclerView.this.generateDefaultLayoutParams();
                    vVar2.itemView.setLayoutParams(layoutParams2);
                } else if (!RecyclerView.this.checkLayoutParams(layoutParams)) {
                    layoutParams2 = (LayoutParams) RecyclerView.this.generateLayoutParams(layoutParams);
                    vVar2.itemView.setLayoutParams(layoutParams2);
                } else {
                    layoutParams2 = (LayoutParams) layoutParams;
                }
                layoutParams2.c = vVar2;
                if (!z3 || !z4) {
                    z5 = false;
                }
                layoutParams2.f = z5;
                return vVar2;
            }
            z4 = false;
            layoutParams = vVar2.itemView.getLayoutParams();
            if (layoutParams != null) {
            }
            layoutParams2.c = vVar2;
            z5 = false;
            layoutParams2.f = z5;
            return vVar2;
        }

        private void e(v vVar) {
            if (RecyclerView.this.isAccessibilityEnabled()) {
                View view = vVar.itemView;
                if (androidx.core.f.r.e(view) == 0) {
                    androidx.core.f.r.b(view, 1);
                }
                if (!androidx.core.f.r.b(view)) {
                    vVar.addFlags(16384);
                    androidx.core.f.r.a(view, RecyclerView.this.mAccessibilityDelegate.b());
                }
            }
        }

        private void f(v vVar) {
            if (vVar.itemView instanceof ViewGroup) {
                a((ViewGroup) vVar.itemView, false);
            }
        }

        private void a(ViewGroup viewGroup, boolean z) {
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                View childAt = viewGroup.getChildAt(childCount);
                if (childAt instanceof ViewGroup) {
                    a((ViewGroup) childAt, true);
                }
            }
            if (z) {
                if (viewGroup.getVisibility() == 4) {
                    viewGroup.setVisibility(0);
                    viewGroup.setVisibility(4);
                    return;
                }
                int visibility = viewGroup.getVisibility();
                viewGroup.setVisibility(4);
                viewGroup.setVisibility(visibility);
            }
        }

        public void a(View view) {
            v childViewHolderInt = RecyclerView.getChildViewHolderInt(view);
            if (childViewHolderInt.isTmpDetached()) {
                RecyclerView.this.removeDetachedView(view, false);
            }
            if (childViewHolderInt.isScrap()) {
                childViewHolderInt.unScrap();
            } else if (childViewHolderInt.wasReturnedFromScrap()) {
                childViewHolderInt.clearReturnedFromScrapFlag();
            }
            b(childViewHolderInt);
        }

        /* access modifiers changed from: package-private */
        public void d() {
            for (int size = this.c.size() - 1; size >= 0; size--) {
                d(size);
            }
            this.c.clear();
            if (RecyclerView.ALLOW_THREAD_GAP_WORK) {
                RecyclerView.this.mPrefetchRegistry.a();
            }
        }

        /* access modifiers changed from: package-private */
        public void d(int i2) {
            a(this.c.get(i2), true);
            this.c.remove(i2);
        }

        /* access modifiers changed from: package-private */
        public void b(v vVar) {
            boolean z;
            boolean z2 = false;
            if (vVar.isScrap() || vVar.itemView.getParent() != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("Scrapped or attached views may not be recycled. isScrap:");
                sb.append(vVar.isScrap());
                sb.append(" isAttached:");
                if (vVar.itemView.getParent() != null) {
                    z2 = true;
                }
                sb.append(z2);
                sb.append(RecyclerView.this.exceptionLabel());
                throw new IllegalArgumentException(sb.toString());
            } else if (vVar.isTmpDetached()) {
                throw new IllegalArgumentException("Tmp detached view should be removed from RecyclerView before it can be recycled: " + vVar + RecyclerView.this.exceptionLabel());
            } else if (!vVar.shouldIgnore()) {
                boolean doesTransientStatePreventRecycling = vVar.doesTransientStatePreventRecycling();
                if ((RecyclerView.this.mAdapter != null && doesTransientStatePreventRecycling && RecyclerView.this.mAdapter.onFailedToRecycleView(vVar)) || vVar.isRecyclable()) {
                    if (this.d <= 0 || vVar.hasAnyOfTheFlags(526)) {
                        z = false;
                    } else {
                        int size = this.c.size();
                        if (size >= this.d && size > 0) {
                            d(0);
                            size--;
                        }
                        if (RecyclerView.ALLOW_THREAD_GAP_WORK && size > 0 && !RecyclerView.this.mPrefetchRegistry.a(vVar.mPosition)) {
                            int i2 = size - 1;
                            while (i2 >= 0) {
                                if (!RecyclerView.this.mPrefetchRegistry.a(this.c.get(i2).mPosition)) {
                                    break;
                                }
                                i2--;
                            }
                            size = i2 + 1;
                        }
                        this.c.add(size, vVar);
                        z = true;
                    }
                    if (!z) {
                        a(vVar, true);
                        z2 = true;
                    }
                } else {
                    z = false;
                }
                RecyclerView.this.mViewInfoStore.g(vVar);
                if (!z && !z2 && doesTransientStatePreventRecycling) {
                    vVar.mOwnerRecyclerView = null;
                }
            } else {
                throw new IllegalArgumentException("Trying to recycle an ignored view holder. You should first call stopIgnoringView(view) before calling recycle." + RecyclerView.this.exceptionLabel());
            }
        }

        /* access modifiers changed from: package-private */
        public void a(v vVar, boolean z) {
            RecyclerView.clearNestedRecyclerViewIfNotNested(vVar);
            if (vVar.hasAnyOfTheFlags(16384)) {
                vVar.setFlags(0, 16384);
                androidx.core.f.r.a(vVar.itemView, (androidx.core.f.a) null);
            }
            if (z) {
                d(vVar);
            }
            vVar.mOwnerRecyclerView = null;
            g().a(vVar);
        }

        /* access modifiers changed from: package-private */
        public void b(View view) {
            v childViewHolderInt = RecyclerView.getChildViewHolderInt(view);
            childViewHolderInt.mScrapContainer = null;
            childViewHolderInt.mInChangeScrap = false;
            childViewHolderInt.clearReturnedFromScrapFlag();
            b(childViewHolderInt);
        }

        /* access modifiers changed from: package-private */
        public void c(View view) {
            v childViewHolderInt = RecyclerView.getChildViewHolderInt(view);
            if (!childViewHolderInt.hasAnyOfTheFlags(12) && childViewHolderInt.isUpdated() && !RecyclerView.this.canReuseUpdatedViewHolder(childViewHolderInt)) {
                if (this.b == null) {
                    this.b = new ArrayList<>();
                }
                childViewHolderInt.setScrapContainer(this, true);
                this.b.add(childViewHolderInt);
            } else if (!childViewHolderInt.isInvalid() || childViewHolderInt.isRemoved() || RecyclerView.this.mAdapter.hasStableIds()) {
                childViewHolderInt.setScrapContainer(this, false);
                this.a.add(childViewHolderInt);
            } else {
                throw new IllegalArgumentException("Called scrap view with an invalid view. Invalid views cannot be reused from scrap, they should rebound from recycler pool." + RecyclerView.this.exceptionLabel());
            }
        }

        /* access modifiers changed from: package-private */
        public void c(v vVar) {
            if (vVar.mInChangeScrap) {
                this.b.remove(vVar);
            } else {
                this.a.remove(vVar);
            }
            vVar.mScrapContainer = null;
            vVar.mInChangeScrap = false;
            vVar.clearReturnedFromScrapFlag();
        }

        /* access modifiers changed from: package-private */
        public int e() {
            return this.a.size();
        }

        /* access modifiers changed from: package-private */
        public View e(int i2) {
            return this.a.get(i2).itemView;
        }

        /* access modifiers changed from: package-private */
        public void f() {
            this.a.clear();
            ArrayList<v> arrayList = this.b;
            if (arrayList != null) {
                arrayList.clear();
            }
        }

        /* access modifiers changed from: package-private */
        public v f(int i2) {
            int size;
            int b2;
            ArrayList<v> arrayList = this.b;
            if (arrayList == null || (size = arrayList.size()) == 0) {
                return null;
            }
            for (int i3 = 0; i3 < size; i3++) {
                v vVar = this.b.get(i3);
                if (!vVar.wasReturnedFromScrap() && vVar.getLayoutPosition() == i2) {
                    vVar.addFlags(32);
                    return vVar;
                }
            }
            if (RecyclerView.this.mAdapter.hasStableIds() && (b2 = RecyclerView.this.mAdapterHelper.b(i2)) > 0 && b2 < RecyclerView.this.mAdapter.getItemCount()) {
                long itemId = RecyclerView.this.mAdapter.getItemId(b2);
                for (int i4 = 0; i4 < size; i4++) {
                    v vVar2 = this.b.get(i4);
                    if (!vVar2.wasReturnedFromScrap() && vVar2.getItemId() == itemId) {
                        vVar2.addFlags(32);
                        return vVar2;
                    }
                }
            }
            return null;
        }

        /* access modifiers changed from: package-private */
        public v b(int i2, boolean z) {
            View c2;
            int size = this.a.size();
            for (int i3 = 0; i3 < size; i3++) {
                v vVar = this.a.get(i3);
                if (!vVar.wasReturnedFromScrap() && vVar.getLayoutPosition() == i2 && !vVar.isInvalid() && (RecyclerView.this.mState.g || !vVar.isRemoved())) {
                    vVar.addFlags(32);
                    return vVar;
                }
            }
            if (z || (c2 = RecyclerView.this.mChildHelper.c(i2)) == null) {
                int size2 = this.c.size();
                for (int i4 = 0; i4 < size2; i4++) {
                    v vVar2 = this.c.get(i4);
                    if (!vVar2.isInvalid() && vVar2.getLayoutPosition() == i2) {
                        if (!z) {
                            this.c.remove(i4);
                        }
                        return vVar2;
                    }
                }
                return null;
            }
            v childViewHolderInt = RecyclerView.getChildViewHolderInt(c2);
            RecyclerView.this.mChildHelper.e(c2);
            int b2 = RecyclerView.this.mChildHelper.b(c2);
            if (b2 != -1) {
                RecyclerView.this.mChildHelper.e(b2);
                c(c2);
                childViewHolderInt.addFlags(8224);
                return childViewHolderInt;
            }
            throw new IllegalStateException("layout index should not be -1 after unhiding a view:" + childViewHolderInt + RecyclerView.this.exceptionLabel());
        }

        /* access modifiers changed from: package-private */
        public v a(long j, int i2, boolean z) {
            for (int size = this.a.size() - 1; size >= 0; size--) {
                v vVar = this.a.get(size);
                if (vVar.getItemId() == j && !vVar.wasReturnedFromScrap()) {
                    if (i2 == vVar.getItemViewType()) {
                        vVar.addFlags(32);
                        if (vVar.isRemoved() && !RecyclerView.this.mState.a()) {
                            vVar.setFlags(2, 14);
                        }
                        return vVar;
                    } else if (!z) {
                        this.a.remove(size);
                        RecyclerView.this.removeDetachedView(vVar.itemView, false);
                        b(vVar.itemView);
                    }
                }
            }
            int size2 = this.c.size();
            while (true) {
                size2--;
                if (size2 < 0) {
                    return null;
                }
                v vVar2 = this.c.get(size2);
                if (vVar2.getItemId() == j) {
                    if (i2 == vVar2.getItemViewType()) {
                        if (!z) {
                            this.c.remove(size2);
                        }
                        return vVar2;
                    } else if (!z) {
                        d(size2);
                        return null;
                    }
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void d(v vVar) {
            if (RecyclerView.this.mRecyclerListener != null) {
                RecyclerView.this.mRecyclerListener.a(vVar);
            }
            if (RecyclerView.this.mAdapter != null) {
                RecyclerView.this.mAdapter.onViewRecycled(vVar);
            }
            if (RecyclerView.this.mState != null) {
                RecyclerView.this.mViewInfoStore.g(vVar);
            }
        }

        /* access modifiers changed from: package-private */
        public void a(a aVar, a aVar2, boolean z) {
            a();
            g().a(aVar, aVar2, z);
        }

        /* access modifiers changed from: package-private */
        public void a(int i2, int i3) {
            int i4;
            int i5;
            int i6;
            if (i2 < i3) {
                i6 = i2;
                i5 = i3;
                i4 = -1;
            } else {
                i5 = i2;
                i6 = i3;
                i4 = 1;
            }
            int size = this.c.size();
            for (int i7 = 0; i7 < size; i7++) {
                v vVar = this.c.get(i7);
                if (vVar != null && vVar.mPosition >= i6 && vVar.mPosition <= i5) {
                    if (vVar.mPosition == i2) {
                        vVar.offsetPosition(i3 - i2, false);
                    } else {
                        vVar.offsetPosition(i4, false);
                    }
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void b(int i2, int i3) {
            int size = this.c.size();
            for (int i4 = 0; i4 < size; i4++) {
                v vVar = this.c.get(i4);
                if (vVar != null && vVar.mPosition >= i2) {
                    vVar.offsetPosition(i3, true);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void a(int i2, int i3, boolean z) {
            int i4 = i2 + i3;
            for (int size = this.c.size() - 1; size >= 0; size--) {
                v vVar = this.c.get(size);
                if (vVar != null) {
                    if (vVar.mPosition >= i4) {
                        vVar.offsetPosition(-i3, z);
                    } else if (vVar.mPosition >= i2) {
                        vVar.addFlags(8);
                        d(size);
                    }
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void a(t tVar) {
            this.i = tVar;
        }

        /* access modifiers changed from: package-private */
        public void a(n nVar) {
            n nVar2 = this.e;
            if (nVar2 != null) {
                nVar2.c();
            }
            this.e = nVar;
            if (this.e != null && RecyclerView.this.getAdapter() != null) {
                this.e.b();
            }
        }

        /* access modifiers changed from: package-private */
        public n g() {
            if (this.e == null) {
                this.e = new n();
            }
            return this.e;
        }

        /* access modifiers changed from: package-private */
        public void c(int i2, int i3) {
            int i4;
            int i5 = i3 + i2;
            for (int size = this.c.size() - 1; size >= 0; size--) {
                v vVar = this.c.get(size);
                if (vVar != null && (i4 = vVar.mPosition) >= i2 && i4 < i5) {
                    vVar.addFlags(2);
                    d(size);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void h() {
            int size = this.c.size();
            for (int i2 = 0; i2 < size; i2++) {
                v vVar = this.c.get(i2);
                if (vVar != null) {
                    vVar.addFlags(6);
                    vVar.addChangePayload(null);
                }
            }
            if (RecyclerView.this.mAdapter == null || !RecyclerView.this.mAdapter.hasStableIds()) {
                d();
            }
        }

        /* access modifiers changed from: package-private */
        public void i() {
            int size = this.c.size();
            for (int i2 = 0; i2 < size; i2++) {
                this.c.get(i2).clearOldPosition();
            }
            int size2 = this.a.size();
            for (int i3 = 0; i3 < size2; i3++) {
                this.a.get(i3).clearOldPosition();
            }
            ArrayList<v> arrayList = this.b;
            if (arrayList != null) {
                int size3 = arrayList.size();
                for (int i4 = 0; i4 < size3; i4++) {
                    this.b.get(i4).clearOldPosition();
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void j() {
            int size = this.c.size();
            for (int i2 = 0; i2 < size; i2++) {
                LayoutParams layoutParams = (LayoutParams) this.c.get(i2).itemView.getLayoutParams();
                if (layoutParams != null) {
                    layoutParams.e = true;
                }
            }
        }
    }

    public static abstract class a<VH extends v> {
        private boolean mHasStableIds = false;
        private final b mObservable = new b();

        public abstract int getItemCount();

        public long getItemId(int i) {
            return -1;
        }

        public int getItemViewType(int i) {
            return 0;
        }

        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        }

        public abstract void onBindViewHolder(VH vh, int i);

        public abstract VH onCreateViewHolder(ViewGroup viewGroup, int i);

        public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        }

        public boolean onFailedToRecycleView(VH vh) {
            return false;
        }

        public void onViewAttachedToWindow(VH vh) {
        }

        public void onViewDetachedFromWindow(VH vh) {
        }

        public void onViewRecycled(VH vh) {
        }

        public void onBindViewHolder(VH vh, int i, List<Object> list) {
            onBindViewHolder(vh, i);
        }

        public final VH createViewHolder(ViewGroup viewGroup, int i) {
            try {
                androidx.core.os.c.a(RecyclerView.TRACE_CREATE_VIEW_TAG);
                VH onCreateViewHolder = onCreateViewHolder(viewGroup, i);
                if (onCreateViewHolder.itemView.getParent() == null) {
                    onCreateViewHolder.mItemViewType = i;
                    return onCreateViewHolder;
                }
                throw new IllegalStateException("ViewHolder views must not be attached when created. Ensure that you are not passing 'true' to the attachToRoot parameter of LayoutInflater.inflate(..., boolean attachToRoot)");
            } finally {
                androidx.core.os.c.a();
            }
        }

        public final void bindViewHolder(VH vh, int i) {
            vh.mPosition = i;
            if (hasStableIds()) {
                vh.mItemId = getItemId(i);
            }
            vh.setFlags(1, 519);
            androidx.core.os.c.a(RecyclerView.TRACE_BIND_VIEW_TAG);
            onBindViewHolder(vh, i, vh.getUnmodifiedPayloads());
            vh.clearPayload();
            ViewGroup.LayoutParams layoutParams = vh.itemView.getLayoutParams();
            if (layoutParams instanceof LayoutParams) {
                ((LayoutParams) layoutParams).e = true;
            }
            androidx.core.os.c.a();
        }

        public void setHasStableIds(boolean z) {
            if (!hasObservers()) {
                this.mHasStableIds = z;
                return;
            }
            throw new IllegalStateException("Cannot change whether this adapter has stable IDs while the adapter has registered observers.");
        }

        public final boolean hasStableIds() {
            return this.mHasStableIds;
        }

        public final boolean hasObservers() {
            return this.mObservable.a();
        }

        public void registerAdapterDataObserver(c cVar) {
            this.mObservable.registerObserver(cVar);
        }

        public void unregisterAdapterDataObserver(c cVar) {
            this.mObservable.unregisterObserver(cVar);
        }

        public final void notifyDataSetChanged() {
            this.mObservable.b();
        }

        public final void notifyItemChanged(int i) {
            this.mObservable.a(i, 1);
        }

        public final void notifyItemChanged(int i, Object obj) {
            this.mObservable.a(i, 1, obj);
        }

        public final void notifyItemRangeChanged(int i, int i2) {
            this.mObservable.a(i, i2);
        }

        public final void notifyItemRangeChanged(int i, int i2, Object obj) {
            this.mObservable.a(i, i2, obj);
        }

        public final void notifyItemInserted(int i) {
            this.mObservable.b(i, 1);
        }

        public final void notifyItemMoved(int i, int i2) {
            this.mObservable.d(i, i2);
        }

        public final void notifyItemRangeInserted(int i, int i2) {
            this.mObservable.b(i, i2);
        }

        public final void notifyItemRemoved(int i) {
            this.mObservable.c(i, 1);
        }

        public final void notifyItemRangeRemoved(int i, int i2) {
            this.mObservable.c(i, i2);
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchChildDetached(View view) {
        v childViewHolderInt = getChildViewHolderInt(view);
        onChildDetachedFromWindow(view);
        a aVar = this.mAdapter;
        if (!(aVar == null || childViewHolderInt == null)) {
            aVar.onViewDetachedFromWindow(childViewHolderInt);
        }
        List<j> list = this.mOnChildAttachStateListeners;
        if (list != null) {
            for (int size = list.size() - 1; size >= 0; size--) {
                this.mOnChildAttachStateListeners.get(size).b(view);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchChildAttached(View view) {
        v childViewHolderInt = getChildViewHolderInt(view);
        onChildAttachedToWindow(view);
        a aVar = this.mAdapter;
        if (!(aVar == null || childViewHolderInt == null)) {
            aVar.onViewAttachedToWindow(childViewHolderInt);
        }
        List<j> list = this.mOnChildAttachStateListeners;
        if (list != null) {
            for (int size = list.size() - 1; size >= 0; size--) {
                this.mOnChildAttachStateListeners.get(size).a(view);
            }
        }
    }

    public static abstract class i {
        private final m.b a = new m.b() {
            /* class androidx.recyclerview.widget.RecyclerView.i.AnonymousClass1 */

            @Override // androidx.recyclerview.widget.m.b
            public View a(int i) {
                return i.this.i(i);
            }

            @Override // androidx.recyclerview.widget.m.b
            public int a() {
                return i.this.A();
            }

            @Override // androidx.recyclerview.widget.m.b
            public int b() {
                return i.this.y() - i.this.C();
            }

            @Override // androidx.recyclerview.widget.m.b
            public int a(View view) {
                return i.this.h(view) - ((LayoutParams) view.getLayoutParams()).leftMargin;
            }

            @Override // androidx.recyclerview.widget.m.b
            public int b(View view) {
                return i.this.j(view) + ((LayoutParams) view.getLayoutParams()).rightMargin;
            }
        };
        private final m.b b = new m.b() {
            /* class androidx.recyclerview.widget.RecyclerView.i.AnonymousClass2 */

            @Override // androidx.recyclerview.widget.m.b
            public View a(int i) {
                return i.this.i(i);
            }

            @Override // androidx.recyclerview.widget.m.b
            public int a() {
                return i.this.B();
            }

            @Override // androidx.recyclerview.widget.m.b
            public int b() {
                return i.this.z() - i.this.D();
            }

            @Override // androidx.recyclerview.widget.m.b
            public int a(View view) {
                return i.this.i(view) - ((LayoutParams) view.getLayoutParams()).topMargin;
            }

            @Override // androidx.recyclerview.widget.m.b
            public int b(View view) {
                return i.this.k(view) + ((LayoutParams) view.getLayoutParams()).bottomMargin;
            }
        };
        private boolean c = true;
        private boolean d = true;
        private int e;
        private int f;
        private int g;
        private int h;
        b p;
        RecyclerView q;
        m r = new m(this.a);
        m s = new m(this.b);
        r t;
        boolean u = false;
        boolean v = false;
        boolean w = false;
        int x;
        boolean y;

        public interface a {
            void b(int i, int i2);
        }

        public static class b {
            public int a;
            public int b;
            public boolean c;
            public boolean d;
        }

        public int a(int i, o oVar, s sVar) {
            return 0;
        }

        public View a(View view, int i, o oVar, s sVar) {
            return null;
        }

        public abstract LayoutParams a();

        public void a(int i, int i2, s sVar, a aVar) {
        }

        public void a(int i, a aVar) {
        }

        public void a(Parcelable parcelable) {
        }

        public void a(a aVar, a aVar2) {
        }

        public void a(s sVar) {
        }

        public void a(RecyclerView recyclerView) {
        }

        public void a(RecyclerView recyclerView, int i, int i2) {
        }

        public void a(RecyclerView recyclerView, int i, int i2, int i3) {
        }

        public boolean a(LayoutParams layoutParams) {
            return layoutParams != null;
        }

        public boolean a(o oVar, s sVar, View view, int i, Bundle bundle) {
            return false;
        }

        public boolean a(RecyclerView recyclerView, ArrayList<View> arrayList, int i, int i2) {
            return false;
        }

        public int b(int i, o oVar, s sVar) {
            return 0;
        }

        public void b(RecyclerView recyclerView, int i, int i2) {
        }

        public boolean b() {
            return false;
        }

        public int c(s sVar) {
            return 0;
        }

        public void c(RecyclerView recyclerView, int i, int i2) {
        }

        public int d(o oVar, s sVar) {
            return 0;
        }

        public int d(s sVar) {
            return 0;
        }

        public Parcelable d() {
            return null;
        }

        public View d(View view, int i) {
            return null;
        }

        public void d(RecyclerView recyclerView) {
        }

        public int e(s sVar) {
            return 0;
        }

        public void e(int i) {
        }

        @Deprecated
        public void e(RecyclerView recyclerView) {
        }

        public boolean e() {
            return false;
        }

        public boolean e(o oVar, s sVar) {
            return false;
        }

        public int f(s sVar) {
            return 0;
        }

        public boolean f() {
            return false;
        }

        public int g(s sVar) {
            return 0;
        }

        public int h(s sVar) {
            return 0;
        }

        public void l(int i) {
        }

        /* access modifiers changed from: package-private */
        public boolean l() {
            return false;
        }

        public int u() {
            return -1;
        }

        /* access modifiers changed from: package-private */
        public void b(RecyclerView recyclerView) {
            if (recyclerView == null) {
                this.q = null;
                this.p = null;
                this.g = 0;
                this.h = 0;
            } else {
                this.q = recyclerView;
                this.p = recyclerView.mChildHelper;
                this.g = recyclerView.getWidth();
                this.h = recyclerView.getHeight();
            }
            this.e = 1073741824;
            this.f = 1073741824;
        }

        /* access modifiers changed from: package-private */
        public void c(int i, int i2) {
            this.g = View.MeasureSpec.getSize(i);
            this.e = View.MeasureSpec.getMode(i);
            if (this.e == 0 && !RecyclerView.ALLOW_SIZE_IN_UNSPECIFIED_SPEC) {
                this.g = 0;
            }
            this.h = View.MeasureSpec.getSize(i2);
            this.f = View.MeasureSpec.getMode(i2);
            if (this.f == 0 && !RecyclerView.ALLOW_SIZE_IN_UNSPECIFIED_SPEC) {
                this.h = 0;
            }
        }

        /* access modifiers changed from: package-private */
        public void d(int i, int i2) {
            int v2 = v();
            if (v2 == 0) {
                this.q.defaultOnMeasure(i, i2);
                return;
            }
            int i3 = Api.BaseClientBuilder.API_PRIORITY_OTHER;
            int i4 = Api.BaseClientBuilder.API_PRIORITY_OTHER;
            int i5 = androidx.customview.a.a.INVALID_ID;
            int i6 = androidx.customview.a.a.INVALID_ID;
            for (int i7 = 0; i7 < v2; i7++) {
                View i8 = i(i7);
                Rect rect = this.q.mTempRect;
                a(i8, rect);
                if (rect.left < i3) {
                    i3 = rect.left;
                }
                if (rect.right > i5) {
                    i5 = rect.right;
                }
                if (rect.top < i4) {
                    i4 = rect.top;
                }
                if (rect.bottom > i6) {
                    i6 = rect.bottom;
                }
            }
            this.q.mTempRect.set(i3, i4, i5, i6);
            a(this.q.mTempRect, i, i2);
        }

        public void a(Rect rect, int i, int i2) {
            f(a(i, rect.width() + A() + C(), F()), a(i2, rect.height() + B() + D(), G()));
        }

        public void o() {
            RecyclerView recyclerView = this.q;
            if (recyclerView != null) {
                recyclerView.requestLayout();
            }
        }

        public static int a(int i, int i2, int i3) {
            int mode = View.MeasureSpec.getMode(i);
            int size = View.MeasureSpec.getSize(i);
            if (mode != Integer.MIN_VALUE) {
                return mode != 1073741824 ? Math.max(i2, i3) : size;
            }
            return Math.min(size, Math.max(i2, i3));
        }

        public void a(String str) {
            RecyclerView recyclerView = this.q;
            if (recyclerView != null) {
                recyclerView.assertNotInLayoutOrScroll(str);
            }
        }

        public boolean c() {
            return this.w;
        }

        public final boolean p() {
            return this.d;
        }

        /* access modifiers changed from: package-private */
        public void c(RecyclerView recyclerView) {
            this.v = true;
            d(recyclerView);
        }

        /* access modifiers changed from: package-private */
        public void b(RecyclerView recyclerView, o oVar) {
            this.v = false;
            a(recyclerView, oVar);
        }

        public boolean q() {
            return this.v;
        }

        public boolean a(Runnable runnable) {
            RecyclerView recyclerView = this.q;
            if (recyclerView != null) {
                return recyclerView.removeCallbacks(runnable);
            }
            return false;
        }

        public void a(RecyclerView recyclerView, o oVar) {
            e(recyclerView);
        }

        public boolean r() {
            RecyclerView recyclerView = this.q;
            return recyclerView != null && recyclerView.mClipToPadding;
        }

        public void c(o oVar, s sVar) {
            Log.e(RecyclerView.TAG, "You must override onLayoutChildren(Recycler recycler, State state) ");
        }

        public LayoutParams a(ViewGroup.LayoutParams layoutParams) {
            if (layoutParams instanceof LayoutParams) {
                return new LayoutParams((LayoutParams) layoutParams);
            }
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                return new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams);
            }
            return new LayoutParams(layoutParams);
        }

        public LayoutParams a(Context context, AttributeSet attributeSet) {
            return new LayoutParams(context, attributeSet);
        }

        public void a(RecyclerView recyclerView, s sVar, int i) {
            Log.e(RecyclerView.TAG, "You must override smoothScrollToPosition to support smooth scrolling");
        }

        public void a(r rVar) {
            r rVar2 = this.t;
            if (!(rVar2 == null || rVar == rVar2 || !rVar2.h())) {
                this.t.f();
            }
            this.t = rVar;
            this.t.a(this.q, this);
        }

        public boolean s() {
            r rVar = this.t;
            return rVar != null && rVar.h();
        }

        public int t() {
            return androidx.core.f.r.f(this.q);
        }

        public void a(View view) {
            a(view, -1);
        }

        public void a(View view, int i) {
            a(view, i, true);
        }

        public void b(View view) {
            b(view, -1);
        }

        public void b(View view, int i) {
            a(view, i, false);
        }

        private void a(View view, int i, boolean z) {
            v childViewHolderInt = RecyclerView.getChildViewHolderInt(view);
            if (z || childViewHolderInt.isRemoved()) {
                this.q.mViewInfoStore.e(childViewHolderInt);
            } else {
                this.q.mViewInfoStore.f(childViewHolderInt);
            }
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            if (childViewHolderInt.wasReturnedFromScrap() || childViewHolderInt.isScrap()) {
                if (childViewHolderInt.isScrap()) {
                    childViewHolderInt.unScrap();
                } else {
                    childViewHolderInt.clearReturnedFromScrapFlag();
                }
                this.p.a(view, i, view.getLayoutParams(), false);
            } else if (view.getParent() == this.q) {
                int b2 = this.p.b(view);
                if (i == -1) {
                    i = this.p.b();
                }
                if (b2 == -1) {
                    throw new IllegalStateException("Added View has RecyclerView as parent but view is not a real child. Unfiltered index:" + this.q.indexOfChild(view) + this.q.exceptionLabel());
                } else if (b2 != i) {
                    this.q.mLayout.e(b2, i);
                }
            } else {
                this.p.a(view, i, false);
                layoutParams.e = true;
                r rVar = this.t;
                if (rVar != null && rVar.h()) {
                    this.t.b(view);
                }
            }
            if (layoutParams.f) {
                childViewHolderInt.itemView.invalidate();
                layoutParams.f = false;
            }
        }

        public void c(View view) {
            this.p.a(view);
        }

        public void g(int i) {
            if (i(i) != null) {
                this.p.a(i);
            }
        }

        public int d(View view) {
            return ((LayoutParams) view.getLayoutParams()).f();
        }

        public View e(View view) {
            View findContainingItemView;
            RecyclerView recyclerView = this.q;
            if (recyclerView == null || (findContainingItemView = recyclerView.findContainingItemView(view)) == null || this.p.c(findContainingItemView)) {
                return null;
            }
            return findContainingItemView;
        }

        public View c(int i) {
            int v2 = v();
            for (int i2 = 0; i2 < v2; i2++) {
                View i3 = i(i2);
                v childViewHolderInt = RecyclerView.getChildViewHolderInt(i3);
                if (childViewHolderInt != null && childViewHolderInt.getLayoutPosition() == i && !childViewHolderInt.shouldIgnore() && (this.q.mState.a() || !childViewHolderInt.isRemoved())) {
                    return i3;
                }
            }
            return null;
        }

        public void h(int i) {
            a(i, i(i));
        }

        private void a(int i, View view) {
            this.p.e(i);
        }

        public void a(View view, int i, LayoutParams layoutParams) {
            v childViewHolderInt = RecyclerView.getChildViewHolderInt(view);
            if (childViewHolderInt.isRemoved()) {
                this.q.mViewInfoStore.e(childViewHolderInt);
            } else {
                this.q.mViewInfoStore.f(childViewHolderInt);
            }
            this.p.a(view, i, layoutParams, childViewHolderInt.isRemoved());
        }

        public void c(View view, int i) {
            a(view, i, (LayoutParams) view.getLayoutParams());
        }

        public void e(int i, int i2) {
            View i3 = i(i);
            if (i3 != null) {
                h(i);
                c(i3, i2);
                return;
            }
            throw new IllegalArgumentException("Cannot move a child from non-existing index:" + i + this.q.toString());
        }

        public void a(View view, o oVar) {
            c(view);
            oVar.a(view);
        }

        public void a(int i, o oVar) {
            View i2 = i(i);
            g(i);
            oVar.a(i2);
        }

        public int v() {
            b bVar = this.p;
            if (bVar != null) {
                return bVar.b();
            }
            return 0;
        }

        public View i(int i) {
            b bVar = this.p;
            if (bVar != null) {
                return bVar.b(i);
            }
            return null;
        }

        public int w() {
            return this.e;
        }

        public int x() {
            return this.f;
        }

        public int y() {
            return this.g;
        }

        public int z() {
            return this.h;
        }

        public int A() {
            RecyclerView recyclerView = this.q;
            if (recyclerView != null) {
                return recyclerView.getPaddingLeft();
            }
            return 0;
        }

        public int B() {
            RecyclerView recyclerView = this.q;
            if (recyclerView != null) {
                return recyclerView.getPaddingTop();
            }
            return 0;
        }

        public int C() {
            RecyclerView recyclerView = this.q;
            if (recyclerView != null) {
                return recyclerView.getPaddingRight();
            }
            return 0;
        }

        public int D() {
            RecyclerView recyclerView = this.q;
            if (recyclerView != null) {
                return recyclerView.getPaddingBottom();
            }
            return 0;
        }

        public View E() {
            View focusedChild;
            RecyclerView recyclerView = this.q;
            if (recyclerView == null || (focusedChild = recyclerView.getFocusedChild()) == null || this.p.c(focusedChild)) {
                return null;
            }
            return focusedChild;
        }

        public void j(int i) {
            RecyclerView recyclerView = this.q;
            if (recyclerView != null) {
                recyclerView.offsetChildrenHorizontal(i);
            }
        }

        public void k(int i) {
            RecyclerView recyclerView = this.q;
            if (recyclerView != null) {
                recyclerView.offsetChildrenVertical(i);
            }
        }

        public void a(o oVar) {
            for (int v2 = v() - 1; v2 >= 0; v2--) {
                a(oVar, v2, i(v2));
            }
        }

        private void a(o oVar, int i, View view) {
            v childViewHolderInt = RecyclerView.getChildViewHolderInt(view);
            if (!childViewHolderInt.shouldIgnore()) {
                if (!childViewHolderInt.isInvalid() || childViewHolderInt.isRemoved() || this.q.mAdapter.hasStableIds()) {
                    h(i);
                    oVar.c(view);
                    this.q.mViewInfoStore.h(childViewHolderInt);
                    return;
                }
                g(i);
                oVar.b(childViewHolderInt);
            }
        }

        /* access modifiers changed from: package-private */
        public void b(o oVar) {
            int e2 = oVar.e();
            for (int i = e2 - 1; i >= 0; i--) {
                View e3 = oVar.e(i);
                v childViewHolderInt = RecyclerView.getChildViewHolderInt(e3);
                if (!childViewHolderInt.shouldIgnore()) {
                    childViewHolderInt.setIsRecyclable(false);
                    if (childViewHolderInt.isTmpDetached()) {
                        this.q.removeDetachedView(e3, false);
                    }
                    if (this.q.mItemAnimator != null) {
                        this.q.mItemAnimator.d(childViewHolderInt);
                    }
                    childViewHolderInt.setIsRecyclable(true);
                    oVar.b(e3);
                }
            }
            oVar.f();
            if (e2 > 0) {
                this.q.invalidate();
            }
        }

        /* access modifiers changed from: package-private */
        public boolean a(View view, int i, int i2, LayoutParams layoutParams) {
            return !this.c || !b(view.getMeasuredWidth(), i, layoutParams.width) || !b(view.getMeasuredHeight(), i2, layoutParams.height);
        }

        /* access modifiers changed from: package-private */
        public boolean b(View view, int i, int i2, LayoutParams layoutParams) {
            return view.isLayoutRequested() || !this.c || !b(view.getWidth(), i, layoutParams.width) || !b(view.getHeight(), i2, layoutParams.height);
        }

        private static boolean b(int i, int i2, int i3) {
            int mode = View.MeasureSpec.getMode(i2);
            int size = View.MeasureSpec.getSize(i2);
            if (i3 > 0 && i != i3) {
                return false;
            }
            if (mode == Integer.MIN_VALUE) {
                return size >= i;
            }
            if (mode != 0) {
                return mode == 1073741824 && size == i;
            }
            return true;
        }

        public void a(View view, int i, int i2) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            Rect itemDecorInsetsForChild = this.q.getItemDecorInsetsForChild(view);
            int i3 = i + itemDecorInsetsForChild.left + itemDecorInsetsForChild.right;
            int i4 = i2 + itemDecorInsetsForChild.top + itemDecorInsetsForChild.bottom;
            int a2 = a(y(), w(), A() + C() + layoutParams.leftMargin + layoutParams.rightMargin + i3, layoutParams.width, e());
            int a3 = a(z(), x(), B() + D() + layoutParams.topMargin + layoutParams.bottomMargin + i4, layoutParams.height, f());
            if (b(view, a2, a3, layoutParams)) {
                view.measure(a2, a3);
            }
        }

        public static int a(int i, int i2, int i3, int i4, boolean z) {
            int i5;
            int i6 = i - i3;
            int i7 = 0;
            int max = Math.max(0, i6);
            if (z) {
                if (i4 >= 0) {
                    max = i4;
                    i7 = 1073741824;
                } else if (i4 == -1) {
                    if (i2 != Integer.MIN_VALUE) {
                        if (i2 == 0) {
                            i2 = 0;
                            i5 = 0;
                        } else if (i2 != 1073741824) {
                            i2 = 0;
                            i5 = 0;
                        }
                        i7 = i2;
                        max = i5;
                    }
                    i5 = max;
                    i7 = i2;
                    max = i5;
                } else if (i4 == -2) {
                    max = 0;
                }
                return View.MeasureSpec.makeMeasureSpec(max, i7);
            }
            if (i4 >= 0) {
                max = i4;
                i7 = 1073741824;
            } else if (i4 == -1) {
                i7 = i2;
            } else if (i4 == -2) {
                if (i2 == Integer.MIN_VALUE || i2 == 1073741824) {
                    i7 = androidx.customview.a.a.INVALID_ID;
                }
            }
            return View.MeasureSpec.makeMeasureSpec(max, i7);
            max = 0;
            return View.MeasureSpec.makeMeasureSpec(max, i7);
        }

        public int f(View view) {
            Rect rect = ((LayoutParams) view.getLayoutParams()).d;
            return view.getMeasuredWidth() + rect.left + rect.right;
        }

        public int g(View view) {
            Rect rect = ((LayoutParams) view.getLayoutParams()).d;
            return view.getMeasuredHeight() + rect.top + rect.bottom;
        }

        public void a(View view, int i, int i2, int i3, int i4) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            Rect rect = layoutParams.d;
            view.layout(i + rect.left + layoutParams.leftMargin, i2 + rect.top + layoutParams.topMargin, (i3 - rect.right) - layoutParams.rightMargin, (i4 - rect.bottom) - layoutParams.bottomMargin);
        }

        public void a(View view, boolean z, Rect rect) {
            Matrix matrix;
            if (z) {
                Rect rect2 = ((LayoutParams) view.getLayoutParams()).d;
                rect.set(-rect2.left, -rect2.top, view.getWidth() + rect2.right, view.getHeight() + rect2.bottom);
            } else {
                rect.set(0, 0, view.getWidth(), view.getHeight());
            }
            if (!(this.q == null || (matrix = view.getMatrix()) == null || matrix.isIdentity())) {
                RectF rectF = this.q.mTempRectF;
                rectF.set(rect);
                matrix.mapRect(rectF);
                rect.set((int) Math.floor((double) rectF.left), (int) Math.floor((double) rectF.top), (int) Math.ceil((double) rectF.right), (int) Math.ceil((double) rectF.bottom));
            }
            rect.offset(view.getLeft(), view.getTop());
        }

        public void a(View view, Rect rect) {
            RecyclerView.getDecoratedBoundsWithMarginsInt(view, rect);
        }

        public int h(View view) {
            return view.getLeft() - n(view);
        }

        public int i(View view) {
            return view.getTop() - l(view);
        }

        public int j(View view) {
            return view.getRight() + o(view);
        }

        public int k(View view) {
            return view.getBottom() + m(view);
        }

        public void b(View view, Rect rect) {
            RecyclerView recyclerView = this.q;
            if (recyclerView == null) {
                rect.set(0, 0, 0, 0);
            } else {
                rect.set(recyclerView.getItemDecorInsetsForChild(view));
            }
        }

        public int l(View view) {
            return ((LayoutParams) view.getLayoutParams()).d.top;
        }

        public int m(View view) {
            return ((LayoutParams) view.getLayoutParams()).d.bottom;
        }

        public int n(View view) {
            return ((LayoutParams) view.getLayoutParams()).d.left;
        }

        public int o(View view) {
            return ((LayoutParams) view.getLayoutParams()).d.right;
        }

        private int[] b(RecyclerView recyclerView, View view, Rect rect, boolean z) {
            int[] iArr = new int[2];
            int A = A();
            int B = B();
            int y2 = y() - C();
            int z2 = z() - D();
            int left = (view.getLeft() + rect.left) - view.getScrollX();
            int top = (view.getTop() + rect.top) - view.getScrollY();
            int width = rect.width() + left;
            int height = rect.height() + top;
            int i = left - A;
            int min = Math.min(0, i);
            int i2 = top - B;
            int min2 = Math.min(0, i2);
            int i3 = width - y2;
            int max = Math.max(0, i3);
            int max2 = Math.max(0, height - z2);
            if (t() != 1) {
                max = min != 0 ? min : Math.min(i, max);
            } else if (max == 0) {
                max = Math.max(min, i3);
            }
            if (min2 == 0) {
                min2 = Math.min(i2, max2);
            }
            iArr[0] = max;
            iArr[1] = min2;
            return iArr;
        }

        public boolean a(RecyclerView recyclerView, View view, Rect rect, boolean z) {
            return a(recyclerView, view, rect, z, false);
        }

        public boolean a(RecyclerView recyclerView, View view, Rect rect, boolean z, boolean z2) {
            int[] b2 = b(recyclerView, view, rect, z);
            int i = b2[0];
            int i2 = b2[1];
            if ((z2 && !d(recyclerView, i, i2)) || (i == 0 && i2 == 0)) {
                return false;
            }
            if (z) {
                recyclerView.scrollBy(i, i2);
            } else {
                recyclerView.smoothScrollBy(i, i2);
            }
            return true;
        }

        public boolean a(View view, boolean z, boolean z2) {
            boolean z3 = this.r.a(view, 24579) && this.s.a(view, 24579);
            return z ? z3 : !z3;
        }

        private boolean d(RecyclerView recyclerView, int i, int i2) {
            View focusedChild = recyclerView.getFocusedChild();
            if (focusedChild == null) {
                return false;
            }
            int A = A();
            int B = B();
            int y2 = y() - C();
            int z = z() - D();
            Rect rect = this.q.mTempRect;
            a(focusedChild, rect);
            if (rect.left - i >= y2 || rect.right - i <= A || rect.top - i2 >= z || rect.bottom - i2 <= B) {
                return false;
            }
            return true;
        }

        @Deprecated
        public boolean a(RecyclerView recyclerView, View view, View view2) {
            return s() || recyclerView.isComputingLayout();
        }

        public boolean a(RecyclerView recyclerView, s sVar, View view, View view2) {
            return a(recyclerView, view, view2);
        }

        public void a(RecyclerView recyclerView, int i, int i2, Object obj) {
            c(recyclerView, i, i2);
        }

        public void a(o oVar, s sVar, int i, int i2) {
            this.q.defaultOnMeasure(i, i2);
        }

        public void f(int i, int i2) {
            this.q.setMeasuredDimension(i, i2);
        }

        public int F() {
            return androidx.core.f.r.j(this.q);
        }

        public int G() {
            return androidx.core.f.r.k(this.q);
        }

        /* access modifiers changed from: package-private */
        public void H() {
            r rVar = this.t;
            if (rVar != null) {
                rVar.f();
            }
        }

        /* access modifiers changed from: package-private */
        public void b(r rVar) {
            if (this.t == rVar) {
                this.t = null;
            }
        }

        public void c(o oVar) {
            for (int v2 = v() - 1; v2 >= 0; v2--) {
                if (!RecyclerView.getChildViewHolderInt(i(v2)).shouldIgnore()) {
                    a(v2, oVar);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void a(androidx.core.f.a.c cVar) {
            a(this.q.mRecycler, this.q.mState, cVar);
        }

        public void a(o oVar, s sVar, androidx.core.f.a.c cVar) {
            if (this.q.canScrollVertically(-1) || this.q.canScrollHorizontally(-1)) {
                cVar.a(8192);
                cVar.h(true);
            }
            if (this.q.canScrollVertically(1) || this.q.canScrollHorizontally(1)) {
                cVar.a(TasDefs.ADDITIONAL_DATA_MAX_LENGTH);
                cVar.h(true);
            }
            cVar.a(c.b.a(a(oVar, sVar), b(oVar, sVar), e(oVar, sVar), d(oVar, sVar)));
        }

        public void a(AccessibilityEvent accessibilityEvent) {
            a(this.q.mRecycler, this.q.mState, accessibilityEvent);
        }

        public void a(o oVar, s sVar, AccessibilityEvent accessibilityEvent) {
            RecyclerView recyclerView = this.q;
            if (recyclerView != null && accessibilityEvent != null) {
                boolean z = true;
                if (!recyclerView.canScrollVertically(1) && !this.q.canScrollVertically(-1) && !this.q.canScrollHorizontally(-1) && !this.q.canScrollHorizontally(1)) {
                    z = false;
                }
                accessibilityEvent.setScrollable(z);
                if (this.q.mAdapter != null) {
                    accessibilityEvent.setItemCount(this.q.mAdapter.getItemCount());
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void a(View view, androidx.core.f.a.c cVar) {
            v childViewHolderInt = RecyclerView.getChildViewHolderInt(view);
            if (childViewHolderInt != null && !childViewHolderInt.isRemoved() && !this.p.c(childViewHolderInt.itemView)) {
                a(this.q.mRecycler, this.q.mState, view, cVar);
            }
        }

        public void a(o oVar, s sVar, View view, androidx.core.f.a.c cVar) {
            cVar.b(c.C٠٠٣٢c.a(f() ? d(view) : 0, 1, e() ? d(view) : 0, 1, false, false));
        }

        public void I() {
            this.u = true;
        }

        public int a(o oVar, s sVar) {
            RecyclerView recyclerView = this.q;
            if (recyclerView == null || recyclerView.mAdapter == null || !f()) {
                return 1;
            }
            return this.q.mAdapter.getItemCount();
        }

        public int b(o oVar, s sVar) {
            RecyclerView recyclerView = this.q;
            if (recyclerView == null || recyclerView.mAdapter == null || !e()) {
                return 1;
            }
            return this.q.mAdapter.getItemCount();
        }

        /* access modifiers changed from: package-private */
        public boolean a(int i, Bundle bundle) {
            return a(this.q.mRecycler, this.q.mState, i, bundle);
        }

        public boolean a(o oVar, s sVar, int i, Bundle bundle) {
            int i2;
            int i3;
            RecyclerView recyclerView = this.q;
            if (recyclerView == null) {
                return false;
            }
            if (i == 4096) {
                i3 = recyclerView.canScrollVertically(1) ? (z() - B()) - D() : 0;
                i2 = this.q.canScrollHorizontally(1) ? (y() - A()) - C() : 0;
            } else if (i != 8192) {
                i3 = 0;
                i2 = 0;
            } else {
                i3 = recyclerView.canScrollVertically(-1) ? -((z() - B()) - D()) : 0;
                i2 = this.q.canScrollHorizontally(-1) ? -((y() - A()) - C()) : 0;
            }
            if (i3 == 0 && i2 == 0) {
                return false;
            }
            this.q.smoothScrollBy(i2, i3);
            return true;
        }

        /* access modifiers changed from: package-private */
        public boolean a(View view, int i, Bundle bundle) {
            return a(this.q.mRecycler, this.q.mState, view, i, bundle);
        }

        public static b a(Context context, AttributeSet attributeSet, int i, int i2) {
            b bVar = new b();
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.RecyclerView, i, i2);
            bVar.a = obtainStyledAttributes.getInt(R.styleable.RecyclerView_android_orientation, 1);
            bVar.b = obtainStyledAttributes.getInt(R.styleable.RecyclerView_spanCount, 1);
            bVar.c = obtainStyledAttributes.getBoolean(R.styleable.RecyclerView_reverseLayout, false);
            bVar.d = obtainStyledAttributes.getBoolean(R.styleable.RecyclerView_stackFromEnd, false);
            obtainStyledAttributes.recycle();
            return bVar;
        }

        /* access modifiers changed from: package-private */
        public void f(RecyclerView recyclerView) {
            c(View.MeasureSpec.makeMeasureSpec(recyclerView.getWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(recyclerView.getHeight(), 1073741824));
        }

        /* access modifiers changed from: package-private */
        public boolean J() {
            int v2 = v();
            for (int i = 0; i < v2; i++) {
                ViewGroup.LayoutParams layoutParams = i(i).getLayoutParams();
                if (layoutParams.width < 0 && layoutParams.height < 0) {
                    return true;
                }
            }
            return false;
        }
    }

    public static abstract class h {
        @Deprecated
        public void a(Canvas canvas, RecyclerView recyclerView) {
        }

        @Deprecated
        public void b(Canvas canvas, RecyclerView recyclerView) {
        }

        public void b(Canvas canvas, RecyclerView recyclerView, s sVar) {
            a(canvas, recyclerView);
        }

        public void a(Canvas canvas, RecyclerView recyclerView, s sVar) {
            b(canvas, recyclerView);
        }

        @Deprecated
        public void a(Rect rect, int i, RecyclerView recyclerView) {
            rect.set(0, 0, 0, 0);
        }

        public void a(Rect rect, View view, RecyclerView recyclerView, s sVar) {
            a(rect, ((LayoutParams) view.getLayoutParams()).f(), recyclerView);
        }
    }

    public static abstract class v {
        static final int FLAG_ADAPTER_FULLUPDATE = 1024;
        static final int FLAG_ADAPTER_POSITION_UNKNOWN = 512;
        static final int FLAG_APPEARED_IN_PRE_LAYOUT = 4096;
        static final int FLAG_BOUNCED_FROM_HIDDEN_LIST = 8192;
        static final int FLAG_BOUND = 1;
        static final int FLAG_IGNORE = 128;
        static final int FLAG_INVALID = 4;
        static final int FLAG_MOVED = 2048;
        static final int FLAG_NOT_RECYCLABLE = 16;
        static final int FLAG_REMOVED = 8;
        static final int FLAG_RETURNED_FROM_SCRAP = 32;
        static final int FLAG_SET_A11Y_ITEM_DELEGATE = 16384;
        static final int FLAG_TMP_DETACHED = 256;
        static final int FLAG_UPDATE = 2;
        private static final List<Object> FULLUPDATE_PAYLOADS = Collections.emptyList();
        static final int PENDING_ACCESSIBILITY_STATE_NOT_SET = -1;
        public final View itemView;
        int mFlags;
        boolean mInChangeScrap = false;
        private int mIsRecyclableCount = 0;
        long mItemId = -1;
        int mItemViewType = -1;
        WeakReference<RecyclerView> mNestedRecyclerView;
        int mOldPosition = -1;
        RecyclerView mOwnerRecyclerView;
        List<Object> mPayloads = null;
        int mPendingAccessibilityState = -1;
        int mPosition = -1;
        int mPreLayoutPosition = -1;
        o mScrapContainer = null;
        v mShadowedHolder = null;
        v mShadowingHolder = null;
        List<Object> mUnmodifiedPayloads = null;
        private int mWasImportantForAccessibilityBeforeHidden = 0;

        public v(View view) {
            if (view != null) {
                this.itemView = view;
                return;
            }
            throw new IllegalArgumentException("itemView may not be null");
        }

        /* access modifiers changed from: package-private */
        public void flagRemovedAndOffsetPosition(int i, int i2, boolean z) {
            addFlags(8);
            offsetPosition(i2, z);
            this.mPosition = i;
        }

        /* access modifiers changed from: package-private */
        public void offsetPosition(int i, boolean z) {
            if (this.mOldPosition == -1) {
                this.mOldPosition = this.mPosition;
            }
            if (this.mPreLayoutPosition == -1) {
                this.mPreLayoutPosition = this.mPosition;
            }
            if (z) {
                this.mPreLayoutPosition += i;
            }
            this.mPosition += i;
            if (this.itemView.getLayoutParams() != null) {
                ((LayoutParams) this.itemView.getLayoutParams()).e = true;
            }
        }

        /* access modifiers changed from: package-private */
        public void clearOldPosition() {
            this.mOldPosition = -1;
            this.mPreLayoutPosition = -1;
        }

        /* access modifiers changed from: package-private */
        public void saveOldPosition() {
            if (this.mOldPosition == -1) {
                this.mOldPosition = this.mPosition;
            }
        }

        /* access modifiers changed from: package-private */
        public boolean shouldIgnore() {
            return (this.mFlags & FLAG_IGNORE) != 0;
        }

        @Deprecated
        public final int getPosition() {
            int i = this.mPreLayoutPosition;
            return i == -1 ? this.mPosition : i;
        }

        public final int getLayoutPosition() {
            int i = this.mPreLayoutPosition;
            return i == -1 ? this.mPosition : i;
        }

        public final int getAdapterPosition() {
            RecyclerView recyclerView = this.mOwnerRecyclerView;
            if (recyclerView == null) {
                return -1;
            }
            return recyclerView.getAdapterPositionFor(this);
        }

        public final int getOldPosition() {
            return this.mOldPosition;
        }

        public final long getItemId() {
            return this.mItemId;
        }

        public final int getItemViewType() {
            return this.mItemViewType;
        }

        /* access modifiers changed from: package-private */
        public boolean isScrap() {
            return this.mScrapContainer != null;
        }

        /* access modifiers changed from: package-private */
        public void unScrap() {
            this.mScrapContainer.c(this);
        }

        /* access modifiers changed from: package-private */
        public boolean wasReturnedFromScrap() {
            return (this.mFlags & 32) != 0;
        }

        /* access modifiers changed from: package-private */
        public void clearReturnedFromScrapFlag() {
            this.mFlags &= -33;
        }

        /* access modifiers changed from: package-private */
        public void clearTmpDetachFlag() {
            this.mFlags &= -257;
        }

        /* access modifiers changed from: package-private */
        public void stopIgnoring() {
            this.mFlags &= -129;
        }

        /* access modifiers changed from: package-private */
        public void setScrapContainer(o oVar, boolean z) {
            this.mScrapContainer = oVar;
            this.mInChangeScrap = z;
        }

        /* access modifiers changed from: package-private */
        public boolean isInvalid() {
            return (this.mFlags & 4) != 0;
        }

        /* access modifiers changed from: package-private */
        public boolean needsUpdate() {
            return (this.mFlags & 2) != 0;
        }

        /* access modifiers changed from: package-private */
        public boolean isBound() {
            return (this.mFlags & 1) != 0;
        }

        /* access modifiers changed from: package-private */
        public boolean isRemoved() {
            return (this.mFlags & 8) != 0;
        }

        /* access modifiers changed from: package-private */
        public boolean hasAnyOfTheFlags(int i) {
            return (i & this.mFlags) != 0;
        }

        /* access modifiers changed from: package-private */
        public boolean isTmpDetached() {
            return (this.mFlags & 256) != 0;
        }

        /* access modifiers changed from: package-private */
        public boolean isAdapterPositionUnknown() {
            return (this.mFlags & FLAG_ADAPTER_POSITION_UNKNOWN) != 0 || isInvalid();
        }

        /* access modifiers changed from: package-private */
        public void setFlags(int i, int i2) {
            this.mFlags = (i & i2) | (this.mFlags & (i2 ^ -1));
        }

        /* access modifiers changed from: package-private */
        public void addFlags(int i) {
            this.mFlags = i | this.mFlags;
        }

        /* access modifiers changed from: package-private */
        public void addChangePayload(Object obj) {
            if (obj == null) {
                addFlags(1024);
            } else if ((1024 & this.mFlags) == 0) {
                createPayloadsIfNeeded();
                this.mPayloads.add(obj);
            }
        }

        private void createPayloadsIfNeeded() {
            if (this.mPayloads == null) {
                this.mPayloads = new ArrayList();
                this.mUnmodifiedPayloads = Collections.unmodifiableList(this.mPayloads);
            }
        }

        /* access modifiers changed from: package-private */
        public void clearPayload() {
            List<Object> list = this.mPayloads;
            if (list != null) {
                list.clear();
            }
            this.mFlags &= -1025;
        }

        /* access modifiers changed from: package-private */
        public List<Object> getUnmodifiedPayloads() {
            if ((this.mFlags & 1024) != 0) {
                return FULLUPDATE_PAYLOADS;
            }
            List<Object> list = this.mPayloads;
            if (list == null || list.size() == 0) {
                return FULLUPDATE_PAYLOADS;
            }
            return this.mUnmodifiedPayloads;
        }

        /* access modifiers changed from: package-private */
        public void resetInternal() {
            this.mFlags = 0;
            this.mPosition = -1;
            this.mOldPosition = -1;
            this.mItemId = -1;
            this.mPreLayoutPosition = -1;
            this.mIsRecyclableCount = 0;
            this.mShadowedHolder = null;
            this.mShadowingHolder = null;
            clearPayload();
            this.mWasImportantForAccessibilityBeforeHidden = 0;
            this.mPendingAccessibilityState = -1;
            RecyclerView.clearNestedRecyclerViewIfNotNested(this);
        }

        /* access modifiers changed from: package-private */
        public void onEnteredHiddenState(RecyclerView recyclerView) {
            int i = this.mPendingAccessibilityState;
            if (i != -1) {
                this.mWasImportantForAccessibilityBeforeHidden = i;
            } else {
                this.mWasImportantForAccessibilityBeforeHidden = androidx.core.f.r.e(this.itemView);
            }
            recyclerView.setChildImportantForAccessibilityInternal(this, 4);
        }

        /* access modifiers changed from: package-private */
        public void onLeftHiddenState(RecyclerView recyclerView) {
            recyclerView.setChildImportantForAccessibilityInternal(this, this.mWasImportantForAccessibilityBeforeHidden);
            this.mWasImportantForAccessibilityBeforeHidden = 0;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("ViewHolder{" + Integer.toHexString(hashCode()) + " position=" + this.mPosition + " id=" + this.mItemId + ", oldPos=" + this.mOldPosition + ", pLpos:" + this.mPreLayoutPosition);
            if (isScrap()) {
                sb.append(" scrap ");
                sb.append(this.mInChangeScrap ? "[changeScrap]" : "[attachedScrap]");
            }
            if (isInvalid()) {
                sb.append(" invalid");
            }
            if (!isBound()) {
                sb.append(" unbound");
            }
            if (needsUpdate()) {
                sb.append(" update");
            }
            if (isRemoved()) {
                sb.append(" removed");
            }
            if (shouldIgnore()) {
                sb.append(" ignored");
            }
            if (isTmpDetached()) {
                sb.append(" tmpDetached");
            }
            if (!isRecyclable()) {
                sb.append(" not recyclable(" + this.mIsRecyclableCount + ")");
            }
            if (isAdapterPositionUnknown()) {
                sb.append(" undefined adapter position");
            }
            if (this.itemView.getParent() == null) {
                sb.append(" no parent");
            }
            sb.append("}");
            return sb.toString();
        }

        public final void setIsRecyclable(boolean z) {
            this.mIsRecyclableCount = z ? this.mIsRecyclableCount - 1 : this.mIsRecyclableCount + 1;
            int i = this.mIsRecyclableCount;
            if (i < 0) {
                this.mIsRecyclableCount = 0;
                Log.e("View", "isRecyclable decremented below 0: unmatched pair of setIsRecyable() calls for " + this);
            } else if (!z && i == 1) {
                this.mFlags |= 16;
            } else if (z && this.mIsRecyclableCount == 0) {
                this.mFlags &= -17;
            }
        }

        public final boolean isRecyclable() {
            return (this.mFlags & 16) == 0 && !androidx.core.f.r.c(this.itemView);
        }

        /* access modifiers changed from: package-private */
        public boolean shouldBeKeptAsChild() {
            return (this.mFlags & 16) != 0;
        }

        /* access modifiers changed from: package-private */
        public boolean doesTransientStatePreventRecycling() {
            return (this.mFlags & 16) == 0 && androidx.core.f.r.c(this.itemView);
        }

        /* access modifiers changed from: package-private */
        public boolean isUpdated() {
            return (this.mFlags & 2) != 0;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean setChildImportantForAccessibilityInternal(v vVar, int i2) {
        if (isComputingLayout()) {
            vVar.mPendingAccessibilityState = i2;
            this.mPendingAccessibilityImportanceChange.add(vVar);
            return false;
        }
        androidx.core.f.r.b(vVar.itemView, i2);
        return true;
    }

    /* access modifiers changed from: package-private */
    public void dispatchPendingImportantForAccessibilityChanges() {
        int i2;
        for (int size = this.mPendingAccessibilityImportanceChange.size() - 1; size >= 0; size--) {
            v vVar = this.mPendingAccessibilityImportanceChange.get(size);
            if (vVar.itemView.getParent() == this && !vVar.shouldIgnore() && (i2 = vVar.mPendingAccessibilityState) != -1) {
                androidx.core.f.r.b(vVar.itemView, i2);
                vVar.mPendingAccessibilityState = -1;
            }
        }
        this.mPendingAccessibilityImportanceChange.clear();
    }

    /* access modifiers changed from: package-private */
    public int getAdapterPositionFor(v vVar) {
        if (vVar.hasAnyOfTheFlags(524) || !vVar.isBound()) {
            return -1;
        }
        return this.mAdapterHelper.c(vVar.mPosition);
    }

    /* access modifiers changed from: package-private */
    public void initFastScroller(StateListDrawable stateListDrawable, Drawable drawable, StateListDrawable stateListDrawable2, Drawable drawable2) {
        if (stateListDrawable == null || drawable == null || stateListDrawable2 == null || drawable2 == null) {
            throw new IllegalArgumentException("Trying to set fast scroller without both required drawables." + exceptionLabel());
        }
        Resources resources = getContext().getResources();
        new d(this, stateListDrawable, drawable, stateListDrawable2, drawable2, resources.getDimensionPixelSize(R.dimen.fastscroll_default_thickness), resources.getDimensionPixelSize(R.dimen.fastscroll_minimum_range), resources.getDimensionPixelOffset(R.dimen.fastscroll_margin));
    }

    public void setNestedScrollingEnabled(boolean z) {
        getScrollingChildHelper().a(z);
    }

    @Override // androidx.core.f.i
    public boolean isNestedScrollingEnabled() {
        return getScrollingChildHelper().a();
    }

    public boolean startNestedScroll(int i2) {
        return getScrollingChildHelper().b(i2);
    }

    public boolean startNestedScroll(int i2, int i3) {
        return getScrollingChildHelper().a(i2, i3);
    }

    @Override // androidx.core.f.i
    public void stopNestedScroll() {
        getScrollingChildHelper().c();
    }

    @Override // androidx.core.f.j
    public void stopNestedScroll(int i2) {
        getScrollingChildHelper().c(i2);
    }

    public boolean hasNestedScrollingParent() {
        return getScrollingChildHelper().b();
    }

    public boolean hasNestedScrollingParent(int i2) {
        return getScrollingChildHelper().a(i2);
    }

    public boolean dispatchNestedScroll(int i2, int i3, int i4, int i5, int[] iArr) {
        return getScrollingChildHelper().a(i2, i3, i4, i5, iArr);
    }

    public boolean dispatchNestedScroll(int i2, int i3, int i4, int i5, int[] iArr, int i6) {
        return getScrollingChildHelper().a(i2, i3, i4, i5, iArr, i6);
    }

    public boolean dispatchNestedPreScroll(int i2, int i3, int[] iArr, int[] iArr2) {
        return getScrollingChildHelper().a(i2, i3, iArr, iArr2);
    }

    public boolean dispatchNestedPreScroll(int i2, int i3, int[] iArr, int[] iArr2, int i4) {
        return getScrollingChildHelper().a(i2, i3, iArr, iArr2, i4);
    }

    public boolean dispatchNestedFling(float f2, float f3, boolean z) {
        return getScrollingChildHelper().a(f2, f3, z);
    }

    public boolean dispatchNestedPreFling(float f2, float f3) {
        return getScrollingChildHelper().a(f2, f3);
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        v c;
        final Rect d = new Rect();
        boolean e = true;
        boolean f = false;

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

        public LayoutParams(LayoutParams layoutParams) {
            super((ViewGroup.LayoutParams) layoutParams);
        }

        public boolean c() {
            return this.c.isInvalid();
        }

        public boolean d() {
            return this.c.isRemoved();
        }

        public boolean e() {
            return this.c.isUpdated();
        }

        public int f() {
            return this.c.getLayoutPosition();
        }
    }

    public static abstract class c {
        public void a() {
        }

        public void a(int i, int i2) {
        }

        public void a(int i, int i2, int i3) {
        }

        public void b(int i, int i2) {
        }

        public void c(int i, int i2) {
        }

        public void a(int i, int i2, Object obj) {
            a(i, i2);
        }
    }

    public static abstract class r {
        private int a = -1;
        private RecyclerView b;
        private i c;
        private boolean d;
        private boolean e;
        private View f;
        private final a g = new a(0, 0);
        private boolean h;

        public interface b {
            PointF d(int i);
        }

        /* access modifiers changed from: protected */
        public abstract void a();

        /* access modifiers changed from: protected */
        public abstract void a(int i, int i2, s sVar, a aVar);

        /* access modifiers changed from: protected */
        public abstract void a(View view, s sVar, a aVar);

        /* access modifiers changed from: protected */
        public abstract void b();

        /* access modifiers changed from: package-private */
        public void a(RecyclerView recyclerView, i iVar) {
            if (this.h) {
                Log.w(RecyclerView.TAG, "An instance of " + getClass().getSimpleName() + " was started " + "more than once. Each instance of" + getClass().getSimpleName() + " " + "is intended to only be used once. You should create a new instance for " + "each use.");
            }
            this.b = recyclerView;
            this.c = iVar;
            if (this.a != -1) {
                this.b.mState.a = this.a;
                this.e = true;
                this.d = true;
                this.f = e(i());
                a();
                this.b.mViewFlinger.a();
                this.h = true;
                return;
            }
            throw new IllegalArgumentException("Invalid target position");
        }

        public void c(int i) {
            this.a = i;
        }

        public PointF d(int i) {
            i e2 = e();
            if (e2 instanceof b) {
                return ((b) e2).d(i);
            }
            Log.w(RecyclerView.TAG, "You should override computeScrollVectorForPosition when the LayoutManager does not implement " + b.class.getCanonicalName());
            return null;
        }

        public i e() {
            return this.c;
        }

        /* access modifiers changed from: protected */
        public final void f() {
            if (this.e) {
                this.e = false;
                b();
                this.b.mState.a = -1;
                this.f = null;
                this.a = -1;
                this.d = false;
                this.c.b(this);
                this.c = null;
                this.b = null;
            }
        }

        public boolean g() {
            return this.d;
        }

        public boolean h() {
            return this.e;
        }

        public int i() {
            return this.a;
        }

        /* access modifiers changed from: package-private */
        public void a(int i, int i2) {
            PointF d2;
            RecyclerView recyclerView = this.b;
            if (!this.e || this.a == -1 || recyclerView == null) {
                f();
            }
            if (!(!this.d || this.f != null || this.c == null || (d2 = d(this.a)) == null || (d2.x == 0.0f && d2.y == 0.0f))) {
                recyclerView.scrollStep((int) Math.signum(d2.x), (int) Math.signum(d2.y), null);
            }
            this.d = false;
            View view = this.f;
            if (view != null) {
                if (a(view) == this.a) {
                    a(this.f, recyclerView.mState, this.g);
                    this.g.a(recyclerView);
                    f();
                } else {
                    Log.e(RecyclerView.TAG, "Passed over target position while smooth scrolling.");
                    this.f = null;
                }
            }
            if (this.e) {
                a(i, i2, recyclerView.mState, this.g);
                boolean a2 = this.g.a();
                this.g.a(recyclerView);
                if (!a2) {
                    return;
                }
                if (this.e) {
                    this.d = true;
                    recyclerView.mViewFlinger.a();
                    return;
                }
                f();
            }
        }

        public int a(View view) {
            return this.b.getChildLayoutPosition(view);
        }

        public int j() {
            return this.b.mLayout.v();
        }

        public View e(int i) {
            return this.b.mLayout.c(i);
        }

        /* access modifiers changed from: protected */
        public void b(View view) {
            if (a(view) == i()) {
                this.f = view;
            }
        }

        /* access modifiers changed from: protected */
        public void a(PointF pointF) {
            float sqrt = (float) Math.sqrt((double) ((pointF.x * pointF.x) + (pointF.y * pointF.y)));
            pointF.x /= sqrt;
            pointF.y /= sqrt;
        }

        public static class a {
            private int a;
            private int b;
            private int c;
            private int d;
            private Interpolator e;
            private boolean f;
            private int g;

            public a(int i, int i2) {
                this(i, i2, androidx.customview.a.a.INVALID_ID, null);
            }

            public a(int i, int i2, int i3, Interpolator interpolator) {
                this.d = -1;
                this.f = false;
                this.g = 0;
                this.a = i;
                this.b = i2;
                this.c = i3;
                this.e = interpolator;
            }

            public void a(int i) {
                this.d = i;
            }

            /* access modifiers changed from: package-private */
            public boolean a() {
                return this.d >= 0;
            }

            /* access modifiers changed from: package-private */
            public void a(RecyclerView recyclerView) {
                int i = this.d;
                if (i >= 0) {
                    this.d = -1;
                    recyclerView.jumpToPositionForSmoothScroller(i);
                    this.f = false;
                } else if (this.f) {
                    b();
                    if (this.e != null) {
                        recyclerView.mViewFlinger.a(this.a, this.b, this.c, this.e);
                    } else if (this.c == Integer.MIN_VALUE) {
                        recyclerView.mViewFlinger.b(this.a, this.b);
                    } else {
                        recyclerView.mViewFlinger.a(this.a, this.b, this.c);
                    }
                    this.g++;
                    if (this.g > 10) {
                        Log.e(RecyclerView.TAG, "Smooth Scroll action is being updated too frequently. Make sure you are not changing it unless necessary");
                    }
                    this.f = false;
                } else {
                    this.g = 0;
                }
            }

            private void b() {
                if (this.e != null && this.c < 1) {
                    throw new IllegalStateException("If you provide an interpolator, you must set a positive duration");
                } else if (this.c < 1) {
                    throw new IllegalStateException("Scroll duration must be a positive number");
                }
            }

            public void a(int i, int i2, int i3, Interpolator interpolator) {
                this.a = i;
                this.b = i2;
                this.c = i3;
                this.e = interpolator;
                this.f = true;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public static class b extends Observable<c> {
        b() {
        }

        public boolean a() {
            return !this.mObservers.isEmpty();
        }

        public void b() {
            for (int size = this.mObservers.size() - 1; size >= 0; size--) {
                ((c) this.mObservers.get(size)).a();
            }
        }

        public void a(int i, int i2) {
            a(i, i2, null);
        }

        public void a(int i, int i2, Object obj) {
            for (int size = this.mObservers.size() - 1; size >= 0; size--) {
                ((c) this.mObservers.get(size)).a(i, i2, obj);
            }
        }

        public void b(int i, int i2) {
            for (int size = this.mObservers.size() - 1; size >= 0; size--) {
                ((c) this.mObservers.get(size)).b(i, i2);
            }
        }

        public void c(int i, int i2) {
            for (int size = this.mObservers.size() - 1; size >= 0; size--) {
                ((c) this.mObservers.get(size)).c(i, i2);
            }
        }

        public void d(int i, int i2) {
            for (int size = this.mObservers.size() - 1; size >= 0; size--) {
                ((c) this.mObservers.get(size)).a(i, i2, 1);
            }
        }
    }

    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() {
            /* class androidx.recyclerview.widget.RecyclerView.SavedState.AnonymousClass1 */

            /* renamed from: a */
            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            /* renamed from: a */
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel, null);
            }

            /* renamed from: a */
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        Parcelable a;

        SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.a = parcel.readParcelable(classLoader == null ? i.class.getClassLoader() : classLoader);
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override // androidx.customview.view.AbsSavedState
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeParcelable(this.a, 0);
        }

        /* access modifiers changed from: package-private */
        public void a(SavedState savedState) {
            this.a = savedState.a;
        }
    }

    public static class s {
        int a = -1;
        int b = 0;
        int c = 0;
        int d = 1;
        int e = 0;
        boolean f = false;
        boolean g = false;
        boolean h = false;
        boolean i = false;
        boolean j = false;
        boolean k = false;
        int l;
        long m;
        int n;
        int o;
        int p;
        private SparseArray<Object> q;

        /* access modifiers changed from: package-private */
        public void a(int i2) {
            if ((this.d & i2) == 0) {
                throw new IllegalStateException("Layout state should be one of " + Integer.toBinaryString(i2) + " but it is " + Integer.toBinaryString(this.d));
            }
        }

        /* access modifiers changed from: package-private */
        public void a(a aVar) {
            this.d = 1;
            this.e = aVar.getItemCount();
            this.g = false;
            this.h = false;
            this.i = false;
        }

        public boolean a() {
            return this.g;
        }

        public boolean b() {
            return this.k;
        }

        public int c() {
            return this.a;
        }

        public boolean d() {
            return this.a != -1;
        }

        public int e() {
            return this.g ? this.b - this.c : this.e;
        }

        public String toString() {
            return "State{mTargetPosition=" + this.a + ", mData=" + this.q + ", mItemCount=" + this.e + ", mIsMeasuring=" + this.i + ", mPreviousLayoutItemCount=" + this.b + ", mDeletedInvisibleItemCountSincePreviousLayout=" + this.c + ", mStructureChanged=" + this.f + ", mInPreLayout=" + this.g + ", mRunSimpleAnimations=" + this.j + ", mRunPredictiveAnimations=" + this.k + JSONTranscoder.OBJ_END;
        }
    }

    private class g implements f.b {
        g() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.f.b
        public void a(v vVar) {
            vVar.setIsRecyclable(true);
            if (vVar.mShadowedHolder != null && vVar.mShadowingHolder == null) {
                vVar.mShadowedHolder = null;
            }
            vVar.mShadowingHolder = null;
            if (!vVar.shouldBeKeptAsChild() && !RecyclerView.this.removeAnimatingView(vVar.itemView) && vVar.isTmpDetached()) {
                RecyclerView.this.removeDetachedView(vVar.itemView, false);
            }
        }
    }

    public static abstract class f {
        private b a = null;
        private ArrayList<a> b = new ArrayList<>();
        private long c = 120;
        private long d = 120;
        private long e = 250;
        private long f = 250;

        public interface a {
            void a();
        }

        /* access modifiers changed from: package-private */
        public interface b {
            void a(v vVar);
        }

        public abstract void a();

        public abstract boolean a(v vVar, c cVar, c cVar2);

        public abstract boolean a(v vVar, v vVar2, c cVar, c cVar2);

        public abstract boolean b();

        public abstract boolean b(v vVar, c cVar, c cVar2);

        public abstract boolean c(v vVar, c cVar, c cVar2);

        public abstract void d();

        public abstract void d(v vVar);

        public void g(v vVar) {
        }

        public boolean h(v vVar) {
            return true;
        }

        public long e() {
            return this.e;
        }

        public long f() {
            return this.c;
        }

        public long g() {
            return this.d;
        }

        public long h() {
            return this.f;
        }

        /* access modifiers changed from: package-private */
        public void a(b bVar) {
            this.a = bVar;
        }

        public c a(s sVar, v vVar, int i, List<Object> list) {
            return j().a(vVar);
        }

        public c a(s sVar, v vVar) {
            return j().a(vVar);
        }

        static int e(v vVar) {
            int i = vVar.mFlags & 14;
            if (vVar.isInvalid()) {
                return 4;
            }
            if ((i & 4) != 0) {
                return i;
            }
            int oldPosition = vVar.getOldPosition();
            int adapterPosition = vVar.getAdapterPosition();
            return (oldPosition == -1 || adapterPosition == -1 || oldPosition == adapterPosition) ? i : i | 2048;
        }

        public final void f(v vVar) {
            g(vVar);
            b bVar = this.a;
            if (bVar != null) {
                bVar.a(vVar);
            }
        }

        public boolean a(v vVar, List<Object> list) {
            return h(vVar);
        }

        public final void i() {
            int size = this.b.size();
            for (int i = 0; i < size; i++) {
                this.b.get(i).a();
            }
            this.b.clear();
        }

        public c j() {
            return new c();
        }

        public static class c {
            public int a;
            public int b;
            public int c;
            public int d;

            public c a(v vVar) {
                return a(vVar, 0);
            }

            public c a(v vVar, int i) {
                View view = vVar.itemView;
                this.a = view.getLeft();
                this.b = view.getTop();
                this.c = view.getRight();
                this.d = view.getBottom();
                return this;
            }
        }
    }

    /* access modifiers changed from: protected */
    public int getChildDrawingOrder(int i2, int i3) {
        d dVar = this.mChildDrawingOrderCallback;
        if (dVar == null) {
            return super.getChildDrawingOrder(i2, i3);
        }
        return dVar.a(i2, i3);
    }

    private androidx.core.f.k getScrollingChildHelper() {
        if (this.mScrollingChildHelper == null) {
            this.mScrollingChildHelper = new androidx.core.f.k(this);
        }
        return this.mScrollingChildHelper;
    }
}
