package apijson.demo.model;

import apijson.MethodAccess;

import static apijson.RequestRole.ADMIN;
import static apijson.RequestRole.UNKNOWN;

@MethodAccess(
        POST = {UNKNOWN, ADMIN},
        DELETE = {ADMIN}
)
public class Stone {
}
