# Copyright (C) 2009 The Android Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE        :=  mdove
#LOCAL_C_INCLUDES    :=  JniKit.h
LOCAL_SRC_FILES     :=  myfirstc.c
include $(BUILD_SHARED_LIBRARY)

#include $(CLEAR_VARS)
#LOCAL_MODULE        :=  udid
#LOCAL_C_INCLUDES    :=  udid.h
#LOCAL_SRC_FILES     :=  udid.c
#include $(BUILD_SHARED_LIBRARY)
