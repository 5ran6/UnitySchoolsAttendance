/*
 * Copyright 2019 Mountedwings Cybersystems LTD. All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package gamint.com.unityschoolsattendance.utils;

import java.io.Serializable;
import java.util.ArrayList;

public class storageFile implements Serializable {

    public static class fingerPrint {
        public static ArrayList<String> allFingerprints = new ArrayList<String>();


        public static void addFingerints(String imageFile) {
            allFingerprints.add(imageFile);
        }


        public static ArrayList<String> getAllFingerprints() {
            return allFingerprints;
        }

    }
}
