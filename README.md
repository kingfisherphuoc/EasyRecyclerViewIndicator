# EasyRecyclerViewIndicator
As the name suggests, you can use this library to show indicator for your `RecyclerView` or any `View` you want easily.
### Installing
Use Gradle:
```gradle
implementation 'com.kingfisherphuoc:easy-recyclerview-indicator:1.2'
```
### How to use?

This is how we declare the indicators in XML (1 horizontal indicator, 1 vertical indicator):
```xml
    <android.support.v7.widget.RecyclerView
        android:id="@+id/recyclerView"
        android:layout_width="0dp"
        android:layout_height="100dp"
        android:text="Hello World!"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent"/>

    <com.kingfisher.easyviewindicator.RecyclerViewIndicator
        android:id="@+id/circleIndicator"
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


    <android.support.v7.widget.RecyclerView
        android:id="@+id/recyclerView2"
        android:layout_width="100dp"
        android:layout_height="0dp"
        android:layout_marginTop="10dp"
        android:text="Hello World!"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintTop_toBottomOf="@id/circleIndicator"/>

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
        app:layout_constraintTop_toBottomOf="@+id/circleIndicator">

    </com.kingfisher.easyviewindicator.AnyViewIndicator>
```

### What's in the next version?
What are the things you want to have in this library? Contact me or create a new issue for it.

### License
Copyright 2018 Doan Hong Phuoc

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
