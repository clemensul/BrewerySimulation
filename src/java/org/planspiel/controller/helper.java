/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.planspiel.controller;

/**
 *
 * @author cleme
 */
public final class helper {
    
    public static synchronized String getGameHash(String cookie) {
        String[] hashes = cookie.split("x");
        return hashes[1];
    }

    public static synchronized String getUserHash(String cookie) {
        String[] hashes = cookie.split("x");
        return hashes[0];
    }
    
    public static String hashItUp(String value) {
        return Integer.toString(value.hashCode());
    }

    public static String concatHashes(String user_hash, String game_hash) {
        return user_hash + "x" + game_hash;
    }
}
