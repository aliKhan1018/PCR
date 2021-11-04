package com.mak.pcr;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseConnection {
    private final String _dbConnectionString = "https://pcrdb-9ed55-default-rtdb.firebaseio.com/";
    private FirebaseDatabase _dbRootNode;
    private FirebaseAuth _firebaseAuth;


    public DatabaseConnection(){
        if (_dbRootNode == null){
            _dbRootNode = FirebaseDatabase.getInstance(_dbConnectionString);
        }
        if(_firebaseAuth == null){
            _firebaseAuth = FirebaseAuth.getInstance();
        }
    }

    public FirebaseDatabase get_dbRootNode(){
        if (_dbRootNode == null){
            _dbRootNode = FirebaseDatabase.getInstance(_dbConnectionString);
        }
        return _dbRootNode;
    }

    public FirebaseAuth get_firebaseAuth(){
        if(_firebaseAuth == null){
            _firebaseAuth = FirebaseAuth.getInstance();
        }
        return _firebaseAuth;
    }

    public Task<AuthResult> CreateUser(String email, String pswd){
        return _firebaseAuth.createUserWithEmailAndPassword(email, pswd);
    }

    public Task<Void> addToDbReference(DatabaseReference dbRef, String childId, Object value){
        return dbRef.child(childId).setValue(value);
    }

    public Task<Void> addToDbReference(String dbRefPath, String childId, Object value){
        DatabaseReference _dbRef = this.get_dbReference(dbRefPath);
        return _dbRef.child(childId).setValue(value);
    }

    public DatabaseReference get_dbReference(String path){
        return _dbRootNode.getReference(path);
    }

}
