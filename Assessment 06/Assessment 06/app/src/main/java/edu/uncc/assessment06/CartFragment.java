package edu.uncc.assessment06;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import edu.uncc.assessment06.databinding.CartRowItemBinding;
import edu.uncc.assessment06.databinding.FragmentCartBinding;
import edu.uncc.assessment06.databinding.ProductRowItemBinding;

public class CartFragment extends Fragment {

    ArrayList<CartProduct> mCart = new ArrayList<>();
    ListenerRegistration listenerRegistration;
    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentCartBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    CartAdapter adapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("My Cart");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        adapter = new CartAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(adapter);
        listenerRegistration = db.collection("cart").whereEqualTo("created_by_user", user.getUid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w("demo", "Listen failed.", error);
                    return;
                }
                mCart.clear();
                for (QueryDocumentSnapshot document : value) {
                    CartProduct prod = document.toObject(CartProduct.class);
                    mCart.add(prod);

                }
                double cartTotal = 0d;
                for(CartProduct cp: mCart){
                    cartTotal += Double.valueOf(cp.price);
                }
                binding.textViewTotal.setText("Total: $"+cartTotal);
                adapter.notifyDataSetChanged();
            }
        });

    }

    class CartAdapter extends RecyclerView.Adapter<CartFragment.CartAdapter.CartViewHolder>{


        @NonNull
        @Override
        public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new CartViewHolder(CartRowItemBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull CartFragment.CartAdapter.CartViewHolder holder, int position) {
            CartProduct product = mCart.get(position);
            holder.setupUI(product);
        }

        @Override
        public int getItemCount() {
            return mCart.size();
        }

        class CartViewHolder extends RecyclerView.ViewHolder{
            CartRowItemBinding mBinding;
            CartProduct mProduct;
            public CartViewHolder(CartRowItemBinding cartRowItemBinding) {
                super(cartRowItemBinding.getRoot());
                mBinding = cartRowItemBinding;
            }

            void setupUI(CartProduct product){
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                this.mProduct = product;
                mBinding.textViewProductName.setText(product.getName());
                mBinding.textViewProductPrice.setText("$" + product.getPrice());
                Picasso.get().load(product.getImg_url()).into(mBinding.imageViewProductIcon);
                if(mProduct.created_by_user.equals(user.getUid())){
                    mBinding.imageViewDeleteFromCart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            db.collection("cart").document(mProduct.doc_id).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getActivity(), "Item deleted!", Toast.LENGTH_SHORT).show();
                                        adapter.notifyDataSetChanged();
                                    }
                                }
                            });
                        }
                    });
                    mBinding.imageViewDeleteFromCart.setVisibility(View.VISIBLE);
                } else {
                    mBinding.imageViewDeleteFromCart.setVisibility(View.INVISIBLE);
                }

            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(listenerRegistration != null){
            listenerRegistration.remove();
        }
    }
}