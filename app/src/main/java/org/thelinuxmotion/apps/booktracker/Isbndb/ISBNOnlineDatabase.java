package org.thelinuxmotion.apps.booktracker.Isbndb;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.thelinuxmotion.apps.booktracker.bookinfo.Book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ISBNOnlineDatabase {

    /**
     * Set the paramaters of the book. Uses an online database given a valid
     * isbn
     *
     * @param isbn Isbn to perform the search query against
     * @return
     */
    public void getBookfromOnlineDB(final String isbn, ISBNDBCallbackInterface callback, Context context) {
        //create a book from the ISBN and online db

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        // Request a string response from the ISBNDB
        ISBNDBRequest stringRequest = new ISBNDBRequest(isbn, callback);

        try {
            Log.d(this.getClass().getName(), stringRequest.getHeaders().toString());
        } catch (AuthFailureError error) {

        }

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
        queue.start();

    }

    public interface ISBNDBCallbackInterface {
        void OnReturnBook(Book book);

    }

    private class ISBNDBRequest extends StringRequest {

        ISBNDBCallbackInterface mCallBack;

        public ISBNDBRequest(String isbn, ISBNDBCallbackInterface callback) {
            this(Request.Method.GET,
                    "https://api.isbndb.com/book/" + isbn,
                    new ISBNDBResponseListener(callback),
                    new ISBNDBErrorListener());
        }

        public ISBNDBRequest(int method, String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
            super(method, url, listener, errorListener);
        }

        @Override
        public String getBodyContentType() {
            return "application/json";
        }

        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {

            super.getHeaders();
            Log.d(this.getClass().getName(), "Setting headers");
            Map<String, String> params = new HashMap<String, String>();
            //params.put("Content-Type","application/json");
            params.put("X-API-KEY", "LDh9APouch4RDsPxCPUZK2zSsFhofYFN11XFxqWY");
            params.put("Host", "api.isbndb.com");
            params.put("Accept", "*/*");
            return params;
        }
    }

    private class ISBNDBErrorListener implements Response.ErrorListener {
        @Override
        public void onErrorResponse(VolleyError error) {
            String TAG = "ErrorListener";
            Log.e(TAG, "We've encountered an error.");
            if (error.getMessage() != null) {
                Log.e("ISBNOnlineDatabase" +
                        "se", error.getMessage());

            }
        }
    }

    private class ISBNDBResponseListener implements Response.Listener<String> {

        private final ISBNDBCallbackInterface mCallback;

        public ISBNDBResponseListener(ISBNDBCallbackInterface callback) {
            mCallback = callback;
        }

        @Override
        public void onResponse(String response) {

            // Add a listener that is passed in that can propagate
            // the retreived book up to to main activityW
            Log.v("ISBNDBOnResponse", "Response is: " + response.toString());
            //Create  a new book
            Book b = getBookFromResponse(response);
            if (mCallback == null)
                throw new RuntimeException("ISBNDBCallbackInterface must not be null");
            mCallback.OnReturnBook(b);

        }

        private ArrayList<Book> getBooksFromResponse(String response) {
            ArrayList<Book> Books = new ArrayList<Book>();

            try {
                // The main JSON body
                JSONObject mainResponse = new JSONObject(response);
                // We have the total number of books returned by the search
                int totalNumBooks = mainResponse.getInt("total");
                // Since we have multiple books we are given an array as the book response
                JSONArray bookArray = mainResponse.getJSONArray("books");
                // create a book object to store the
                Book book = new Book();
                // Loop trough all the books
                for (int i = 0; i < totalNumBooks; i++) {
                    // get the ith book
                    JSONObject obj = bookArray.getJSONObject(i);
                    // set the book object to the retreived data
                    setBookFromJSONObject(book, obj);
                    // add this book to the list
                    Books.add(book);
                }

            } catch (JSONException execption) {
                // remove all books from the list, it may be corrupted
                Books = new ArrayList<Book>();
            }

            return Books;
        }

        /**
         * @param response This is the response from ISBNDB it is expected to be a single book object
         * @return The book that generated from the book JSON object
         */
        private Book getBookFromResponse(String response) {

            Book b = new Book();
            try {

                JSONObject json = (JSONObject) new JSONTokener(response).nextValue();
                //JSONArray arr = new JSONArray(response);
                // if we did not recive a book from the database that mean that
                // one does not exist for the value provided
                JSONObject obj = json.optJSONObject("book");
                if (obj == null)
                    return null; // return a blank book as a result

                setBookFromJSONObject(b, obj);
            } catch (Exception e) {

                // Log.v("Book Response", e.toString());
                Log.e("Book Response", e.getMessage());
                //Log.i("JSON", e.getStackTrace().);
            }
            return b;
        }

        private void setBookFromJSONObject(Book b, JSONObject obj) throws JSONException {
            b.mBookTitle = obj.getString("title");
            b.mPublisher = obj.getString("publisher");
            b.mAuthor = obj.getString("authors");
            b.mIsbn_10 = obj.getString("isbn");
            b.mIsbn_13 = obj.getString("isbn13");
            b.mTotalPages = obj.optString("pages", "0");
            b.mBinding = obj.getString("binding");
            b.mEdition = obj.getString("edition");
            b.mSynopsis = obj.getString("synopsys");
            b.mDatePublished = obj.getString("date_published");
            b.mISBN = b.mIsbn_13;
            b.mPagesCompleted = "0";

            b.LogBook("Book Response success");
        }

    }


}
