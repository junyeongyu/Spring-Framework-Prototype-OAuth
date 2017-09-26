package com.junyeong.yu.prototype.oauth;

import com.junyeong.yu.prototype.oauth.model.AnotherUser;
import com.junyeong.yu.prototype.oauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class UserRestController {

    @Autowired
    UserService userService;  //Service which will do all data retrieval/manipulation work

    //-------------------Retrieve All AnotherUsers--------------------------------------------------------

    @PreAuthorize("#oauth2.hasScope('read')")
    @RequestMapping(value = "/user/", method = RequestMethod.GET)
    public ResponseEntity<List<AnotherUser>> listAllAnotherUsers() {
        List<AnotherUser> users = userService.findAllUsers();
        if(users.isEmpty()){
            return new ResponseEntity<List<AnotherUser>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<AnotherUser>>(users, HttpStatus.OK);
    }


    //-------------------Retrieve Single AnotherUser--------------------------------------------------------

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<AnotherUser> getAnotherUser(@PathVariable("id") long id) {
        System.out.println("Fetching AnotherUser with id " + id);
        AnotherUser user = userService.findById(id);
        if (user == null) {
            System.out.println("AnotherUser with id " + id + " not found");
            return new ResponseEntity<AnotherUser>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<AnotherUser>(user, HttpStatus.OK);
    }



    //-------------------Create a AnotherUser--------------------------------------------------------

    @RequestMapping(value = "/user/", method = RequestMethod.POST)
    public ResponseEntity<Void> createAnotherUser(@RequestBody AnotherUser user, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating AnotherUser " + user.getName());

        if (userService.isUserExist(user)) {
            System.out.println("A AnotherUser with name " + user.getName() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        userService.saveUser(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }


    //------------------- Update a AnotherUser --------------------------------------------------------

    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<AnotherUser> updateAnotherUser(@PathVariable("id") long id, @RequestBody AnotherUser user) {
        System.out.println("Updating AnotherUser " + id);

        AnotherUser currentAnotherUser = userService.findById(id);

        if (currentAnotherUser==null) {
            System.out.println("AnotherUser with id " + id + " not found");
            return new ResponseEntity<AnotherUser>(HttpStatus.NOT_FOUND);
        }

        currentAnotherUser.setName(user.getName());
        currentAnotherUser.setAge(user.getAge());
        currentAnotherUser.setSalary(user.getSalary());

        userService.updateUser(currentAnotherUser);
        return new ResponseEntity<AnotherUser>(currentAnotherUser, HttpStatus.OK);
    }

    //------------------- Delete a AnotherUser --------------------------------------------------------

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<AnotherUser> deleteAnotherUser(@PathVariable("id") long id) {
        System.out.println("Fetching & Deleting AnotherUser with id " + id);

        AnotherUser user = userService.findById(id);
        if (user == null) {
            System.out.println("Unable to delete. AnotherUser with id " + id + " not found");
            return new ResponseEntity<AnotherUser>(HttpStatus.NOT_FOUND);
        }

        userService.deleteUserById(id);
        return new ResponseEntity<AnotherUser>(HttpStatus.NO_CONTENT);
    }


    //------------------- Delete All AnotherUsers --------------------------------------------------------

    @RequestMapping(value = "/user/", method = RequestMethod.DELETE)
    public ResponseEntity<AnotherUser> deleteAllAnotherUsers() {
        System.out.println("Deleting All AnotherUsers");

        userService.deleteAllUsers();
        return new ResponseEntity<AnotherUser>(HttpStatus.NO_CONTENT);
    }
}