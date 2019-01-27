# EasyRecyclerViewIndicator
As the name suggests, you can use this library to show indicator for your `RecyclerView` or any `View` you want easily.

![alt sample](https://user-images.githubusercontent.com/962484/51797528-f1fdd280-2237-11e9-93aa-33dcbb769f98.png)
### Installing
Use Gradle:
```gradle
implementation 'com.kingfisherphuoc:easy-recyclerview-indicator:1.3'
```
### How to use?

This is how we declare the indicators in XML (1 horizontal indicator, 1 vertical indicator, 1 anyview indicator for Grid RecyclerView):
```xml
    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recyclerView"
        android:layout_width="0dp"
        android:layout_height="100dp"
        android:text="Hello World!"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <com.kingfisher.easyviewindicator.RecyclerViewIndicator
        android:id="@+id/recyclerViewIndicator"
        android:layout_width="match_parent"
        android:layout_height="20dp"
        app:avi_animation_enable="true"
        app:avi_drawable="@drawable/blue_radius"
        app:avi_drawable_unselected="@drawable/gray_radius"
        app:avi_height="10dp"
        app:avi_margin="10dp"
        app:avi_width="10dp"
        app:layout_constraintTop_toBottomOf="@+id/recyclerView">

    </com.kingfisher.easyviewindicator.RecyclerViewIndicator>


    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recyclerView2"
        android:layout_width="100dp"
        android:layout_height="0dp"
        android:layout_marginTop="10dp"
        android:text="Hello World!"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintTop_toBottomOf="@id/recyclerViewIndicator" />

    <com.kingfisher.easyviewindicator.AnyViewIndicator
        android:id="@+id/anyViewIndicator"
        android:layout_width="20dp"
        android:layout_height="0dp"
        android:layout_marginTop="10dp"
        app:avi_animation_enable="true"
        app:avi_drawable="@drawable/blue_radius"
        app:avi_drawable_unselected="@drawable/gray_radius"
        app:avi_height="10dp"
        app:avi_margin="10dp"
        app:avi_orientation="vertical"
        app:avi_width="10dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toRightOf="@id/recyclerView2"
        app:layout_constraintTop_toBottomOf="@+id/recyclerViewIndicator">

    </com.kingfisher.easyviewindicator.AnyViewIndicator>

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recyclerView3"
        android:layout_width="0dp"
        android:layout_height="220dp"
        android:layout_marginTop="10dp"
        android:layout_marginBottom="40dp"
        android:text="Hello World!"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toEndOf="@id/anyViewIndicator"
        app:layout_constraintTop_toBottomOf="@id/recyclerViewIndicator" />

    <com.kingfisher.easyviewindicator.AnyViewIndicator
        android:id="@+id/anyViewIndicator2"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="10dp"
        android:gravity="center"
        app:avi_animation_enable="false"
        app:avi_drawable="@drawable/blue_radius"
        app:avi_drawable_unselected="@drawable/gray_radius"
        app:avi_height="10dp"
        app:avi_margin="10dp"
        app:avi_orientation="horizontal"
        app:avi_width="10dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="@id/recyclerView3"
        app:layout_constraintStart_toStartOf="@id/recyclerView3"
        app:layout_constraintTop_toBottomOf="@id/recyclerView3" />
```
For `RecyclerView`, it's straighforward:
```java
LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
recyclerView.setLayoutManager(linearLayoutManager);
recyclerView.setAdapter(new TestAdapter());
horizontalIndicator.setRecyclerView(recyclerView);
// If you need to change the adapter size, you should call this function
recyclerViewIndicator.forceUpdateItemCount();
```
If you want to display the indicator for anything you need, that's how you should do:
```java
recyclerView2.setLayoutManager(new LinearLayoutManager(this));
recyclerView2.setAdapter(new TestAdapter());
verticalIndicator.setItemCount(10);
verticalIndicator.setCurrentPosition(0);
recyclerView2.addOnScrollListener(new RecyclerView.OnScrollListener() {
    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        switch (newState) {
            case RecyclerView.SCROLL_STATE_IDLE:
                int position = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
                verticalIndicator.setCurrentPosition(position);
                break;
        }
    }
});
```
For grid recyclerview, you can combine the indicator with `GridLayoutSnapHelper`:
```java
gridIndicator.setItemCount(2);
gridIndicator.setCurrentPosition(0);
recyclerView3.setLayoutManager(new GridLayoutManager(this, 2, RecyclerView.HORIZONTAL, false));
recyclerView3.setAdapter(new TestAdapter());
GridLayoutSnapHelper gridLayoutSnapHelper = new GridLayoutSnapHelper(6);
gridLayoutSnapHelper.attachToRecyclerView(recyclerView3);


recyclerView3.addOnScrollListener(new RecyclerView.OnScrollListener() {
    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        switch (newState) {
            case RecyclerView.SCROLL_STATE_IDLE:
                int position = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                gridIndicator.setCurrentPosition((int) (Math.ceil(Double.valueOf(position)/ 6) - 1));
                break;
        }
    }
});
```
If you want to customize more, you can read the class: `RecyclerViewIndicator`.

Properties that you can config in your xml:
* app:avi_width
* app:avi_height
* app:avi_margin
* app:avi_drawable
* app:avi_drawable_unselected
* app:avi_animator
* app:avi_animator_reverse
* app:avi_animation_enable (default:true)
* app:avi_orientation (default:horizontal)
* app:avi_gravity (default:center)

### What's in the next version?
What are the things you want to have in this library? Contact me or create a new issue for it.

### Special thanks
Thank Ongakuer as I reused a lot of code of this [ongakuer/CircleIndicator](https://github.com/ongakuer/CircleIndicator)

### License
Copyright 2018 Doan Hong Phuoc

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
