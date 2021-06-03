package ru.mgvk.sqlivuln;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {


    @Autowired
    EntityManager entityManager;

    @Autowired
    Repository repository;

    @RequestMapping(path = "/get", method = RequestMethod.GET)
    @ResponseBody
    public List<User> getSomeData(@RequestParam(required = false) String username,
                                  @RequestParam(required = false) String token) {

        Session hibernateSession = entityManager.unwrap(Session.class);

        return hibernateSession.doReturningWork(connection -> {
                    String queryString = "SELECT * From USER WHERE USERNAME = '" + username + "' and TOKEN= " + token;

                    PreparedStatement query = connection
                            .prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

                    ResultSet results = query.executeQuery();


                    ArrayList<User> list = new ArrayList<>();
                    while (results.next()) {
                        User u = new User();
                        u.setId(results.getInt(1));
                        u.setUsername(results.getString(2));
                        u.setToken(results.getString(3));

                        list.add(u);

                    }

                    return list;

                }

        );


    }


    @GetMapping(path = "/")
    @ResponseBody
    public String empty(){
        return "Logged in!1!!11! Go to /get";
    }

    @RequestMapping(path = "/put", method = RequestMethod.GET)
    @ResponseBody
    public User putSomeData(@RequestParam(required = false) String username,
                            @RequestParam(required = false) String token) {

        User u = new User();
        u.setToken(token);
        u.setUsername(username);

        return repository.save(u);


    }


}