/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.wingene.mallxf.util;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * This provides methods to help Activities load their UI.
 */
public class ActivityUtils {

    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     */
    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment, fragment.getClass().getSimpleName());
        transaction.commit();
    }

    public static void addMulFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                                @NonNull Fragment[] fragments, int showPosition, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragments);
        FragmentTransaction transaction = fragmentManager.beginTransaction().setTransition(4097);
        for (int i = 0; i < fragments.length; i++) {
            Fragment fragment = fragments[i];
            transaction.add(frameId, fragment, fragment.getClass().getSimpleName());
            if (i != showPosition) {
                transaction.hide(fragment);
            }
        }
        transaction.commit();
    }

    public static void showHideFragment(@NonNull FragmentManager fragmentManager,
                                        @NonNull Fragment showFragment, @NonNull Fragment hideFragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(hideFragment).show(showFragment).commit();

    }
}
