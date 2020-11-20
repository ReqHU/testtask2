package com.reqhu.testtask2.util;

import javax.validation.groups.Default;

public interface ValidationGroup {

    interface Create extends Default {}

    interface Update extends Default {}

}
