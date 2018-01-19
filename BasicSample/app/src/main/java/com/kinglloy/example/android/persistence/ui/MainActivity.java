package com.kinglloy.example.android.persistence.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kinglloy.example.android.persistence.R;
import com.kinglloy.example.android.persistence.model.Product;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add product list fragment if this is first creation
        if (savedInstanceState == null) {
            ProductListFragment fragment = new ProductListFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment, ProductListFragment.TAG).commit();
        }
    }

    /**
     * Shows the product detail fragment
     */
    public void show(Product product) {
        ProductFragment productFragment = ProductFragment.forProduct(product.getId());

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("product")
                .replace(R.id.fragment_container, productFragment, null)
                .commit();

    }
}
