package Prevalent;

import com.example.shoppy.Product_OC;

import Model.customer;

public class Prevalent
{

   private static customer currentonlineusers;

   public static String UserPhonekey;




}



/*public class Prevalent

{

   private DatabaseReference orders,tryref;
   private FirebaseAuth mauth;
   private String currentuserid;
   private FirebaseUser firebaseUser;

   public static String currentonlineuser;


   {
      orders = FirebaseDatabase.getInstance().getReference().child("Orders");

      mauth = FirebaseAuth.getInstance();
      currentuserid = mauth.getCurrentUser().getUid();
      firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
      tryref = FirebaseDatabase.getInstance().getReference().child("User").child(currentuserid);

      tryref.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot dataSnapshot)
         {
            currentonlineuser = dataSnapshot.child("phoneno").getValue().toString().trim();

         }

         @Override
         public void onCancelled(@NonNull DatabaseError databaseError) {

         }
      });
   }
}*/
