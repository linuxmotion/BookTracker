package org.thelinuxmotion.apps.booktracker.Isbndb;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.thelinuxmotion.apps.booktracker.Isbndb.models.Book;
import org.thelinuxmotion.apps.booktracker.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ISBNOnlineDatabase {

    /**
     * Set the paramaters of the book. Uses an online database given a valid
     * isbn
     *
     * @param isbn Isbn to perform the search query against
     */
    private String mApiKey = "";
    public void getBookfromOnlineDB(final String isbn, ISBNDBCallbackInterface callback, Context context) {

        mApiKey = context.getString(R.string.api_key);
        //9780134706054
        //create a book from the ISBN and online db
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        // Request a string response from the ISBNDB
        ISBNDBRequest stringRequest = new ISBNDBRequest(isbn, callback);

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
        // queue.start();

    }

    public interface ISBNDBCallbackInterface {
        void OnReturnBook(Book book);

    }

    private class ISBNDBRequest extends StringRequest {

        ISBNDBRequest(String isbn, ISBNDBCallbackInterface callback) {
            this(
                    "https://api.isbndb.com/book/" + isbn,
                    new ISBNDBResponseListener(callback),
                    new ISBNDBErrorListener(callback));
        }

        ISBNDBRequest(String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
            super(Method.GET, url, listener, errorListener);
        }

        @Override
        public String getBodyContentType() {
            return "application/json";
        }

        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {

            super.getHeaders();
            Log.d(this.getClass().getName(), "Setting headers");
            Map<String, String> params = new HashMap<>();
            //params.put("Content-Type","application/json");
            params.put("X-API-KEY", mApiKey);
            params.put("Host", "api.isbndb.com");
            params.put("Accept", "*/*");
            return params;

        }
    }

    private class ISBNDBErrorListener implements Response.ErrorListener {
        private final ISBNDBCallbackInterface mCallback;

        ISBNDBErrorListener(ISBNDBCallbackInterface callback) {
            mCallback = callback;
        }
        @Override
        public void onErrorResponse(VolleyError error) {
            String TAG = "ISBNDBErrorListener";
            Log.e(TAG, "We've encountered an error.");
            if (error != null) {
                if (error.getMessage() != null)
                    Log.e("ISBNOnlineDatabase", error.getMessage());
                StackTraceElement[] trace = error.getStackTrace();
                for (StackTraceElement elm : trace) {
                    Log.e(TAG, elm.getMethodName() + "(" + elm.getLineNumber() + ")");
                }
                Log.e(TAG, "onErrorResponse: network response code" + error.networkResponse.statusCode);

            }
            mCallback.OnReturnBook(null); // return a null book to signal we were not able to add it
        }
    }

    private class ISBNDBResponseListener implements Response.Listener<String> {

        private final ISBNDBCallbackInterface mCallback;

        ISBNDBResponseListener(ISBNDBCallbackInterface callback) {
            mCallback = callback;
        }

        @Override
        public void onResponse(String response) {

            // Add a listener that is passed in that can propagate
            // the retreived book up to to main activityW
            Log.v("ISBNDBOnResponse", "Response is: " + response);
            //Create  a new book
            Book b = getBookFromResponse(response);
            if (mCallback == null)
                throw new RuntimeException("ISBNDBCallbackInterface must not be null");
            mCallback.OnReturnBook(b);

        }

        private ArrayList<Book> getBooksFromResponse(String response) {
            ArrayList<Book> Books = new ArrayList<>();

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
                Books = new ArrayList<>();
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
                    return null; // return a null book as a result

                setBookFromJSONObject(b, obj);
            } catch (Exception e) {

                // Log.v("Book Response", e.toString());
                Log.e("Book Response", e.getMessage());
                //Log.i("JSON", e.getStackTrace().);
                return null; // Return a null. book to the user
            }
            return b;
        }

        private void setBookFromJSONObject(Book b, JSONObject obj) throws JSONException {

            b.mIsbn_13 = obj.optString(Book.mIsbn13Name, "");
            b.mIsbn_10 = obj.optString(Book.mIsbn10Name, "");
            // If both ISBN's are set and are the same blank ISBN it means that no ISBN
            // could be parsed from the retrieved object. Since no isbn can be parsed
            // throw an exception to notify the calling code.
            if (b.mIsbn_10.equals(b.mIsbn_13))
                throw new JSONException("JSON Must have and ISBN 10 or ISBN 13 as a valid object");


            b.mAuthors = obj.optString(Book.mAuthorName);
            b.mBinding = obj.optString(Book.mBindingName);
            b.mEdition = obj.optString(Book.mEditionName);
            b.mPublisher = obj.optString(Book.mPublisherName);
            b.mDatePublished = obj.optString(Book.mDatePublishedName);

            b.mNumPages = obj.optString(Book.mNumPagesName);
            b.mPagesCompleted = obj.optString(Book.mPagesCompletedName, "0");
            b.mBookTitle = obj.optString(Book.mBookTitleName);
            b.mSynopsys = obj.optString(Book.mSynopsysName);
            b.mBookTitleLong = obj.optString(Book.mBookTitleLongName);
            b.mDeweyDecimal = obj.optString(Book.mDeweyDecimalName);
            b.mSubjects = obj.optString(Book.mSubjectsName);
            b.mReviews = obj.optString(Book.mReviewsName);
            b.mOverview = obj.optString(Book.mOverviewName);
            b.mMsrp = obj.optString(Book.mMsrpName);
            b.mLanguage = obj.optString(Book.mLanguageName);
            b.mImage = obj.optString(Book.mImageName);
            b.mFormat = obj.optString(Book.mFormatName);
            b.mExcerpt = obj.optString(Book.mExcerptName);
            b.mEdition = obj.optString(Book.mEditionName);
            b.mDimensions = obj.optString(Book.mDimensionsName);
            b.mISBN = b.mIsbn_13;


            b.LogBook("Book Response success");
        }

    }


}
