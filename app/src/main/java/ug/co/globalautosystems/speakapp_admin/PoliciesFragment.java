package ug.co.globalautosystems.speakapp_admin;


import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class PoliciesFragment extends Fragment {

    private static final int PDF_REQUEST_CODE = 122;
    FloatingActionButton floatingActionButton;
    private ArrayAdapter<String> arrayAdapter;

    public PoliciesFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_policies, container, false);
        ListView categoriesList = root.findViewById(R.id.categoriesList);
        final ArrayList<String> values = new ArrayList<>();
        for (int x = 0; x < 20; x++) {
            values.add("Test document");
        }
        arrayAdapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_selectable_list_item, android.R.id.text1, values);
        categoriesList.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
        floatingActionButton = root.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(intent, PDF_REQUEST_CODE);
            }
        });
        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == PDF_REQUEST_CODE) {
                final ProgressDialog progressDialog=new ProgressDialog(getContext());
                progressDialog.setTitle("Uploading");
                progressDialog.setMessage("Please wait while we upload the file");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setCancelable(false);
                progressDialog.show();
                Uri uri = data.getData();
                String str = uri != null ? uri.toString() : null;
                assert str != null;
                File file = new File(str);
                String path = file.getAbsolutePath();
                String name = null;
                if (str.startsWith("content://")) {
                    try (Cursor cursor = Objects.requireNonNull(getActivity()).getContentResolver().query(uri, null, null, null, null)) {
                        if (cursor != null && cursor.moveToFirst()) {
                            name = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                            Uri fileurl=Uri.fromFile(file);
                            final StorageReference reference= FirebaseStorage.getInstance().getReference();
                            final StorageReference storageReference=reference.child("docs").child(name);
                            InputStream inputStream=new FileInputStream(file);
                            UploadTask task=storageReference.putStream(inputStream);
                            Task<Uri> uriTask=task.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                @Override
                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                    if (!task.isSuccessful()){
                                        throw task.getException();
                                    }
                                    return reference.getDownloadUrl();
                                }
                            })
                                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            if (task.isSuccessful())
                                            {
                                                Uri download=task.getResult();
                                                progressDialog.dismiss();
                                            }
                                        }
                                    });

                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
