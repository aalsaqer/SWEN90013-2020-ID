package com.uom.idecide.util;

import javax.servlet.http.HttpServletRequest;

public class PrivilegeUtil {

    public static String PRIVILEGE = "off";

    public static String checkAdmin(HttpServletRequest request){
        if(PRIVILEGE.equals("on")){
            String token = (String)request.getAttribute("claims_admin");
            if(token==null || "".equals(token)){
                throw new RuntimeException("Require admin user privilege");
            }
            return (String) request.getAttribute("id");
        }else{
            return null;
        }
    }

    public static String checkResearcher(HttpServletRequest request){
        if(PRIVILEGE.equals("on")){
            String token = (String)request.getAttribute("claims_researcher");
            if(token==null || "".equals(token)){
                throw new RuntimeException("Require researcher user privilege");
            }
            return (String) request.getAttribute("id");
        }else{
            return null;
        }
    }

    public static String checkUser(HttpServletRequest request){
        if(PRIVILEGE.equals("on")){
            String token = (String)request.getAttribute("claims_user");
            if(token == null || "".equals(token)){
                throw new RuntimeException("Please login first");
            }
            return (String) request.getAttribute("id");
        }else{
            return null;
        }
    }

    public static String checkIsThisUser(HttpServletRequest request, String userId){
        if(PRIVILEGE.equals("on")){
            String userIdFromJwt = checkUser(request);
            if(!(userId!=null && userIdFromJwt!=null && userIdFromJwt.equals(userId))){
                throw new RuntimeException("You are not who you claim is");
            }
            return userId;
        }else{
            return null;
        }
    }

    public static String checkJwt(HttpServletRequest request){
        if(PRIVILEGE.equals("on")){
            String id = (String)request.getAttribute("id");
            if(id==null || "".equals(id)){
                throw new RuntimeException("Require holding a JWT");
            }
            return (String) request.getAttribute("id");
        }else{
            return null;
        }
    }
}