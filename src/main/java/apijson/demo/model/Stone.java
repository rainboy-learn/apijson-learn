package apijson.demo.model;

import apijson.MethodAccess;

import static apijson.RequestRole.*;

@MethodAccess(
        POST = {LOGIN, ADMIN},
        DELETE = {ADMIN}
)
public class Stone {
}
