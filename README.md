# ViewPager(View 사용)

### 설명
____________________________________________________

![ViewPager(F)](https://github.com/Hooooong/DAY18_ViewPager-F-/blob/master/image/ViewPager.gif)

- View 를 사용한 ViewPager, TabLayout

- Fragment 를 사용한 ViewPager, TabLayout 과 화면 구성은 동일

### KeyPoint
____________________________________________________

- ViewPager 란?

  - 참조 : [Fragment 를 사용한 ViewPager](https://github.com/Hooooong/DAY18_ViewPager-F-)

- Fragment vs View

  - Fragment 인 경우에는 Activity 생명주기 외에도 Fragment 의 생명주기를 관리해야 하기 때문에 불편하다.

  - Fragment 를 이용하는 경우도 있지만 Layout 을 상속받아 ViewPager 에 보여줄 수 있다.(Fragment 인 경우 Fragment 를 상속받음)

- Adpater 구현

  - View 로 구현하기 위해서는 `PagerAdapter` 를 상속받아 구현한다.

  - `instantiateItem()`, `isViewFromObject()`, `destroyItem()`, `getCount()` 를 재정의해야 한다. `instantiateItem()` 은 BaseAdapter의 `getView()` 와 유사하다.

  - `instantiateItem()` 는 Swipe 할 때 최대 3번, 최소 2번이 호출이 된다. 예를 들면 2번 페이지를 보여줄 때, 1, 2, 3 페이지를 한번에 호출해 메모리에 올리기 떄문에 총 3번 호출이 된다.

  - `isViewFromObject()`는 `instantiateItem()` 에서 생성한 객체를 이용할 것인지 여부를 반환 한다. `instantiateItem()`에서 리턴된 object가 View가 맞는지 확인을 한다.

  - `destroyItem()` 는 현재 사용하지 않는 View 를 제거한다. ViewPager 는 3개의 View 를 생성하기 때문에 이전에 사용했던 View 를 제거해준다.

  ```java
  @Override
   public int getCount() {
       return COUNT;
   }

   @Override
   public Object instantiateItem(ViewGroup container, int position) {
       View view = views.get(position);
       // OR
       // 여기서 레이아웃 파일을 inflate해서 view로 만들 수 있다.
       // 현재 코드는 생성자에서 이미 생성되어진 View 를 가져와 사용한다.
       container.addView(view);
       return view;
   }

   @Override
   public boolean isViewFromObject(View view, Object object) {
       return view == object;
   }

   @Override
   public void destroyItem(ViewGroup container, int position, Object object) {
       container.removeView((View)object);
   }  
  ```

### Code Review
____________________________________________________

- MainActivity.java

  - ViewPager 와 TabLayout 의 정의와 Listener 를 설정해 주는 class 이다.

  ```java
  public class MainActivity extends AppCompatActivity {

      ViewPager viewPager;
      TabLayout tabLayout;

      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);

          initView();
          setTabLayout();
          setViewPager();
          initListener();
      }

      private void initView(){
          viewPager = (ViewPager)findViewById(R.id.viewPager);
          tabLayout  = (TabLayout)findViewById(R.id.tabLayout);
      }

      private void setTabLayout(){
          tabLayout.addTab(tabLayout.newTab().setText("ONE"));
          tabLayout.addTab(tabLayout.newTab().setText("TWO"));
          tabLayout.addTab(tabLayout.newTab().setText("THREE"));
          tabLayout.addTab(tabLayout.newTab().setText("FOUR"));
      }
      private void setViewPager(){
          CustomAdapter customAdapter = new CustomAdapter(this);
          viewPager.setAdapter(customAdapter);
      }

      private void initListener(){
          viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
          tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
      }
  }
  ```

- CustomAdapter.java

  - PagerAdapter 를 상속받아 구현하는 Adapter class 이다.

  ```java
  class CustomAdapter extends PagerAdapter{

      private static final int COUNT = 4;
      List<View> views;

      public CustomAdapter(Context context) {
          views = new ArrayList<>();
          views.add(new One(context));
          views.add(new Two(context));
          views.add(new Three(context));
          views.add(new Four(context));
      }

      @Override
      public int getCount() {
          return COUNT;
      }

      @Override
      public Object instantiateItem(ViewGroup container, int position) {
          View view = views.get(position);
          container.addView(view);
          return view;
      }

      @Override
      public boolean isViewFromObject(View view, Object object) {
          return view == object;
      }

      @Override
      public void destroyItem(ViewGroup container, int position, Object object) {
  //        super.destroyItem(container, position, object);
          container.removeView((View)object);
      }
  }  
  ```

- One ~ Four.java

  - ViewPager 의 상세 페이지인 View class 이다.

  - FrameLayout 을 상속받아 View 를 구현한다.

  ```java
  public class One extends FrameLayout {
      public One(@NonNull Context context) {
          super(context);
          initView();
      }

      public One(@NonNull Context context, @Nullable AttributeSet attrs) {
          super(context, attrs);
          initView();
      }

      private void initView(){
          View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_one, null);
          // 로직 처리
          process();
          addView(view);
      }

      private void process(){
          // One View 에 대한 로직 처리   
      }
  }
  ```

- activity_main.xml

  ```xml
  <android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
      xmlns:app="http://schemas.android.com/apk/res-auto"
      xmlns:tools="http://schemas.android.com/tools"
      android:layout_width="match_parent"
      android:layout_height="match_parent"
      tools:context="com.hooooong.tablayoutview.MainActivity">

      <android.support.design.widget.TabLayout
          android:id="@+id/tabLayout"
          android:layout_width="0dp"
          android:layout_height="50dp"
          app:layout_constraintTop_toTopOf="parent"
          app:layout_constraintRight_toRightOf="parent"
          app:layout_constraintLeft_toLeftOf="parent"
          app:layout_constraintBottom_toTopOf="@+id/viewPager"
          />

      <android.support.v4.view.ViewPager
          android:id="@+id/viewPager"
          android:layout_width="0dp"
          android:layout_height="0dp"
          app:layout_constraintBottom_toBottomOf="parent"
          app:layout_constraintLeft_toLeftOf="parent"
          app:layout_constraintRight_toRightOf="parent"
          app:layout_constraintTop_toBottomOf="@+id/tabLayout"
          app:layout_constraintVertical_bias="0.0" />
  </android.support.constraint.ConstraintLayout>
  ```

- fragment_one~four.xml

  ```xml
  <?xml version="1.0" encoding="utf-8"?>
  <FrameLayout
      xmlns:android="http://schemas.android.com/apk/res/android"
      xmlns:app="http://schemas.android.com/apk/res-auto"
      xmlns:tools="http://schemas.android.com/tools"
      android:layout_width="match_parent"
      android:layout_height="match_parent">

      <TextView
          android:text="ONE"
          android:layout_width="wrap_content"
          android:layout_height="wrap_content"
          android:layout_gravity="center"/>
  </FrameLayout>
  ```
