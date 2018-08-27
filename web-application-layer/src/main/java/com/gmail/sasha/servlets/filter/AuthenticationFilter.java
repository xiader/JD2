package com.gmail.sasha.servlets.filter;


import com.gmail.sasha.dao.model.RoleEnum;
import com.gmail.sasha.servlets.model.CommandEnum;
import com.gmail.sasha.servlets.model.UserPrincipal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class AuthenticationFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(AuthenticationFilter.class);

    private static final Set<CommandEnum> USER_AVAILABLE = new HashSet<>();
    private static final Set<CommandEnum> ADMIN_AVAILABLE = new HashSet<>();
    private static final String LOGIN_PATH = "/index.html";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("AuthenticationFilter initialized");
        USER_AVAILABLE.add(CommandEnum.ITEMS);

        ADMIN_AVAILABLE.add(CommandEnum.USERS);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        String command = req.getParameter("command");
        if (session == null) {
            defaultRequest(request, response, chain, req, res, command);
        } else {
            UserPrincipal user = (UserPrincipal) session.getAttribute("user");
            if (user == null) {
                defaultRequest(request, response, chain, req, res, command);
            } else {
                CommandEnum commandEnum = CommandEnum.getCommand(command);
                RoleEnum role = user.getRole();
                switch (role) {
                    case USER:
                        if (USER_AVAILABLE.contains(commandEnum)) {
                            chain.doFilter(request, response);
                        } else {
                            session.removeAttribute("user");
                            res.sendRedirect(req.getContextPath() + LOGIN_PATH);
                        }
                        break;
                    case ADMIN:
                        if (ADMIN_AVAILABLE.contains(commandEnum)) {
                            chain.doFilter(request, response);
                        } else {
                            session.removeAttribute("user");
                            res.sendRedirect(req.getContextPath() + LOGIN_PATH);
                        }
                        break;
                    default:
                        session.removeAttribute("user");
                        res.sendRedirect(req.getContextPath() + LOGIN_PATH);
                        break;
                }
            }
        }
    }

    private void defaultRequest(ServletRequest request, ServletResponse response, FilterChain chain, HttpServletRequest req, HttpServletResponse res, String command) throws IOException, ServletException {
        if (req.getMethod().equals("POST")) {
            if (CommandEnum.getCommand(command) == CommandEnum.LOGIN) {
                chain.doFilter(request, response);
            } else {
                res.sendRedirect(req.getContextPath() + LOGIN_PATH);
            }
        } else {
            res.sendRedirect(req.getContextPath() + LOGIN_PATH);
        }
    }

    @Override
    public void destroy() {
    }
}