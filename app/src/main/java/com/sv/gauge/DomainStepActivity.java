package com.sv.gauge;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.VideoView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.sv.gauge.controller.AppEnvironmentValues;
import com.sv.gauge.controller.GaugeDatabaseHelper;
import com.sv.gauge.model.DomainStepObject;
import com.sv.gauge.model.TransactionDetailObject;
import com.sv.gauge.model.TransactionObject;

import java.util.Date;

public class DomainStepActivity extends AppCompatActivity {
    //
    public static final String DOMAIN_ID_KEY = "DOMAIN_ID";
    public static final String DOMAIN_NAME_KEY = "DOMAIN_NAME";
    public static final String DOMAIN_REPORT_DESCRIPTION_KRY = "DOMAIN_REPORT_DESCRIPTION";
    public static final String FIRST_STEP_ID_KEY = "FIRST_STEP_ID";
    public static final String IS_RESUME_KEY = "IS_RESUME";
    public static final String PREVIOUS_TRANSACTION_ID_KEY = "PREVIOUS_TRANSACTION_ID";
    //
    private static final String PICTURE_EXTENSION = ".jpg";
    private static final String VIDEO_EXTENSION = ".mp4";


    //
    private static final String MEDIA_FOLDER = "/sdcard/gauge/media/";

    //components
    private TextView txtHeaderTitle;
    private ImageView imgVisualImage;
    private VideoView vdoVisualVideo;
    private TextView txtVisualDescription;
    private RadioGroup chkValueBoolean;
    private RadioButton radValueBooleanOk;
    private RadioButton radValueBooleanNotOk;
    private EditText txtValueDecimal;
    private EditText txtValueInteger;
    private Button btnComment;
    private Button btnNext;
    private Button btnBack;
    private Button btnFinish;
    //database
    private GaugeDatabaseHelper databaseHelper;
    //local fields
    private int domainId;
    private String domainName;
    private String domainReportDescription;
    private int firstStep;
    private Integer transactionId;
    private Integer currentStepId;
    private Integer previousStepId;
    private DomainStepObject currentStepObject;
    private boolean enableBack = false;
    private String comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_domain_step);

        //database
        this.databaseHelper = new GaugeDatabaseHelper(this);

        //components
        this.txtHeaderTitle = (TextView) findViewById(R.id.txt_header_title);
        this.imgVisualImage = (ImageView) findViewById(R.id.img_visual_image);
        this.vdoVisualVideo = (VideoView) findViewById(R.id.vdo_visual_video);
        this.txtVisualDescription = (TextView) findViewById(R.id.txt_visual_description);
        this.chkValueBoolean = (RadioGroup) findViewById(R.id.chk_value_boolean);
        this.radValueBooleanOk = (RadioButton) findViewById(R.id.value_boolean_ok);
        this.radValueBooleanNotOk = (RadioButton) findViewById(R.id.value_boolean_not_ok);
        this.txtValueDecimal = (EditText) findViewById(R.id.txt_value_decimal);
        this.txtValueInteger = (EditText) findViewById(R.id.txt_value_integer);
        this.btnComment = (Button) findViewById(R.id.btn_comment);
        this.btnBack = (Button) findViewById(R.id.btn_back);
        this.btnNext = (Button) findViewById(R.id.btn_next);
        this.btnFinish = (Button) findViewById(R.id.btn_finish);

        //actions
        this.btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptComment();
            }
        });

        this.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptBack();
            }
        });

        this.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptNext();
            }
        });

        this.btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptFinish();
            }
        });

        //initialize
        attemptStart();
    }

    private void attemptStart() {
        this.enableBack = false;

        Intent intent = getIntent();
        domainId = intent.getIntExtra(DOMAIN_ID_KEY, -1);
        domainName = intent.getStringExtra(DOMAIN_NAME_KEY);
        domainReportDescription = intent.getStringExtra(DOMAIN_REPORT_DESCRIPTION_KRY);
        this.transactionId = intent.getIntExtra(PREVIOUS_TRANSACTION_ID_KEY, -1);
        firstStep = intent.getIntExtra(FIRST_STEP_ID_KEY, -1);

        this.txtHeaderTitle.setText(domainName);

        //limit go previous step
        this.previousStepId = null;

        TransactionObject transactionObject = databaseHelper.getIncompleteTransactionObject(domainId);

        if (transactionObject != null) {
            final TransactionObject finalTransactionObject = transactionObject;
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setMessage(R.string.incomplete_domain)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            transactionId = finalTransactionObject.getId();
                            firstStep = finalTransactionObject.getCurrentStepId();
                            gotoStep(firstStep);
                        }
                    })
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            transactionId = finalTransactionObject.getId();
                            String endTime = AppEnvironmentValues.SIMPLE_DATE_TIME_FORMAT.format(new Date());

                            databaseHelper.updateTransactionStatus(transactionId, endTime, TransactionObject.TRANSACTION_STATUS_CANCEL);
                            startNewTransaction();
                        }
                    })
                    .setCancelable(false)
                    .create();
            alertDialog.show();

            this.transactionId = transactionObject.getId();
        } else {
            startNewTransaction();
        }
    }

    private void startNewTransaction() {
        Log.d("DOMAIN_REPORT_DESCRI", domainReportDescription);

        TransactionObject transactionObject = new TransactionObject();
        transactionObject.setDomainId(domainId);
        transactionObject.setDomainName(domainName);
        transactionObject.setReportDescription(domainReportDescription);
        transactionObject.setDate(AppEnvironmentValues.SIMPLE_DATE_FORMAT.format(new Date()));
        transactionObject.setStartTime(AppEnvironmentValues.SIMPLE_TIME_FORMAT.format(new Date()));
        transactionObject.setEndTime("N/A");
        transactionObject.setUserName(AppEnvironmentValues.USER_NAME);
        transactionObject.setUserId(AppEnvironmentValues.USER_ID);
        transactionObject.setStatus(TransactionObject.TRANSACTION_STATUS_PENDING);

        //insert transaction into database and get id
        this.transactionId = this.databaseHelper.insertTransaction(transactionObject);

        //goto first step
        Log.d("FIRST_STEP", String.valueOf(firstStep));
        gotoStep(firstStep);
    }

    private void attemptNext() {
        this.enableBack = true;

        //check validation
        String value = getCurrentValue();
        if (!isValueValid(currentStepObject.getValueType(), value)) {
            //error value
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setMessage(getString(R.string.value_confirmation))
                    .setCancelable(false)
                    .setPositiveButton(R.string.ok, null)
                    .setCancelable(false)
                    .create();
            alertDialog.show();
        } else {
            if (!currentStepObject.getValueType().equals(DomainStepObject.VALUE_TYPE_NONE)) {
                boolean warning = isNotWarning(value);
                //show warning message
                if (!warning) {
                    AlertDialog alertDialog = new AlertDialog.Builder(this)
                            .setMessage(R.string.value_out_of_range)
                            .setTitle(R.string.warning)
                            .setIcon(R.drawable.ic_warning)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setCancelable(false)
                            .create();
                    alertDialog.show();
                }


                TransactionDetailObject transactionDetailObject = new TransactionDetailObject();
                transactionDetailObject.setTransaction(transactionId);
                transactionDetailObject.setMainGroup(currentStepObject.getMainGroup());
                transactionDetailObject.setSubGroup(currentStepObject.getSubGroup());
                transactionDetailObject.setDomainStep(currentStepObject.getId());
                transactionDetailObject.setNumber(currentStepObject.getNumber());
                transactionDetailObject.setDescription(currentStepObject.getReportDescription());
                transactionDetailObject.setValue(value);
                transactionDetailObject.setUnit(currentStepObject.getUnit());
                transactionDetailObject.setDefaultValue(getDefaultValue());
                transactionDetailObject.setComment(comment);
                transactionDetailObject.setWarning(!warning);
                transactionDetailObject.setTime(AppEnvironmentValues.SIMPLE_DATE_TIME_FORMAT.format(new Date()));

                //insert current step values to database
                int id = databaseHelper.insertTransactionDetail(transactionDetailObject);
            }

            //go to next step
            int nextStep = currentStepObject.getNextStep();
            gotoStep(nextStep);
            databaseHelper.updateTransactionCurrentStep(transactionId, nextStep);
            Log.d("NEXT_STEP", String.valueOf(nextStep));
        }
    }

    private void attemptBack() {
        this.enableBack = false;

        //check validation
        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //goto previous step
                gotoStep(previousStepId);

                //limit go back only once
                previousStepId = null;
            }
        };

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setMessage(R.string.go_back_confirmation)
                .setPositiveButton(R.string.yes, onClickListener)
                .setNegativeButton(R.string.no, null)
                .setCancelable(false)
                .create();
        alertDialog.show();
    }

    private void attemptFinish() {
        this.enableBack = false;

        //update transaction as complete
        String endTime = AppEnvironmentValues.SIMPLE_TIME_FORMAT.format(new Date());

        databaseHelper.updateTransactionStatus(transactionId, endTime, TransactionObject.TRANSACTION_STATUS_COMPLETE);
        finish();
    }

    private void attemptComment() {
        final EditText editText = new EditText(this);
        editText.setText(comment);

        new AlertDialog.Builder(this)
                .setTitle("Enter Comment")
                .setMessage("Please enter your comment for current step.")
                .setView(editText)
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        comment = editText.getText().toString();
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setCancelable(false)
                .show();
    }


    /**
     * @return valid user input for current step
     */
    private String getCurrentValue() {
        String value;
        if (currentStepObject.getValueType().equals(DomainStepObject.VALUE_TYPE_BOOLEAN)) {
//            value = String.valueOf(chkValueBoolean.getSe() ? "YES" : "NO");
            value = "";
            if (radValueBooleanOk.isChecked()) {
                value = "OK";
            }
            if (radValueBooleanNotOk.isChecked()) {
                value = "NOT OK";
            }

        } else if (currentStepObject.getValueType().equals(DomainStepObject.VALUE_TYPE_INTEGER)) {
            value = txtValueInteger.getText().toString();
        } else if (currentStepObject.getValueType().equals(DomainStepObject.VALUE_TYPE_DECIMAL)) {
            value = txtValueDecimal.getText().toString();
        } else if (currentStepObject.getValueType().equals(DomainStepObject.VALUE_TYPE_NONE)) {
            value = "";
        } else {
            value = null;
//            throw new AssertionError("Value type not recognized " + currentStepObject.getValueType());
        }

        return value;
    }

    private boolean isValueValid(String valueType, String value) {
        return !valueType.equals(DomainStepObject.VALUE_TYPE_NONE)
                ? !value.isEmpty()
                : true;
    }

    private boolean isNotWarning(String value) {
        boolean notWarning = true;

        if (currentStepObject.getDefaultValue() != null ? !currentStepObject.getDefaultValue().isEmpty() : false) {
            //compare equal
            notWarning = value.equals(currentStepObject.getDefaultValue());
        }

        if (currentStepObject.getDefaultFromValue() != null ? !currentStepObject.getDefaultFromValue().isEmpty() : false
                && currentStepObject.getDefaultToValue() != null ? !currentStepObject.getDefaultToValue().isEmpty() : false
                ) {
            //compare between
            switch (currentStepObject.getValueType()) {
                case DomainStepObject.VALUE_TYPE_DECIMAL:
                    Double fromValueDecimal = Double.parseDouble(currentStepObject.getDefaultFromValue());
                    Double toValueDecimal = Double.parseDouble(currentStepObject.getDefaultToValue());
                    Double valueDecimal = Double.parseDouble(value);

                    Log.d("DECIMAL-FROM,TO,VALUE", fromValueDecimal + "," + toValueDecimal+ "," + valueDecimal);

                    notWarning = valueDecimal >= fromValueDecimal && toValueDecimal >= valueDecimal;
                    Log.d("WARNING-DECIMAL", String.valueOf(notWarning));
                    break;
                case DomainStepObject.VALUE_TYPE_INTEGER:
                    Integer fromValueInteger = Integer.parseInt(currentStepObject.getDefaultFromValue());
                    Integer toValueInteger = Integer.parseInt(currentStepObject.getDefaultToValue());
                    Integer valueInteger = Integer.parseInt(value);

                    notWarning = valueInteger >= fromValueInteger && toValueInteger >= toValueInteger;
                    Log.d("WARNING-INTEGER", String.valueOf(notWarning));
                    break;
            }
        }

        Log.d("WARNING", String.valueOf(notWarning));

        return notWarning;
    }

    private String getDefaultValue() {
        String defaultValue = "";

        if (currentStepObject.getDefaultValue() != null ? !currentStepObject.getDefaultValue().isEmpty() : false) {
            //compare equal
            defaultValue = currentStepObject.getDefaultValue() + currentStepObject.getUnit();
        }

        if (currentStepObject.getDefaultFromValue() != null ? !currentStepObject.getDefaultFromValue().isEmpty() : false
                && currentStepObject.getDefaultToValue() != null ? !currentStepObject.getDefaultToValue().isEmpty() : false
                ) {

            defaultValue = "BETWEEN " + currentStepObject.getDefaultFromValue() + " " + currentStepObject.getUnit()
                    + " AND " + currentStepObject.getDefaultToValue() + " " + currentStepObject.getUnit();
        }

        Log.d("DEFAULT VAL", String.valueOf(defaultValue));

        return defaultValue;
    }

    private void gotoStep(int stepId) {
        hideKeyBoard();

        //update current step
        this.previousStepId = currentStepId;
        this.currentStepId = stepId;

        //read next step
        currentStepObject = getDomainStepObject(currentStepId);

        //update ui
        //title
        this.txtHeaderTitle.setText(currentStepObject.getTitle());

        String fileName = MEDIA_FOLDER + currentStepObject.getVisualLink();
        switch (currentStepObject.getVisualType()) {
            case DomainStepObject.VISUAL_TYPE_VIDEO:
                fileName = fileName + VIDEO_EXTENSION;
                this.imgVisualImage.setVisibility(View.GONE);
                this.vdoVisualVideo.setVisibility(View.VISIBLE);

                //set video here
                if (currentStepObject.getVisualLink() != null) {
                    this.vdoVisualVideo.setVideoPath(fileName);
                    this.vdoVisualVideo.setMediaController(new MediaController(this));
                    this.vdoVisualVideo.start();
                }

                break;
            case DomainStepObject.VISUAL_TYPE_IMAGE:
                fileName = fileName + PICTURE_EXTENSION;
                this.imgVisualImage.setVisibility(View.VISIBLE);
                this.vdoVisualVideo.setVisibility(View.GONE);

                //set image here
                if (currentStepObject.getVisualLink() != null? !currentStepObject.getVisualLink().isEmpty(): false) {

                   Ion.with(this).load(fileName).withBitmap().asBitmap().setCallback(new FutureCallback<Bitmap>() {
                        @Override
                        public void onCompleted(Exception e, Bitmap result) {
                            imgVisualImage.setImageBitmap(result);
                        }
                    });
                    Log.d("LOAD IMAGE", fileName);
                    Log.d("VISI", imgVisualImage.getVisibility() == View.VISIBLE ? "VISIBLE" : "NOT VISIBLE");
                }
                break;
            case DomainStepObject.VISUAL_TYPE_NONE:
                this.imgVisualImage.setVisibility(View.GONE);
                this.vdoVisualVideo.setVisibility(View.GONE);
                break;
            default:
                throw new AssertionError("Visual Type Not Recognized");
        }
        this.txtVisualDescription.setText(currentStepObject.getVisualDescription());
        //reset fields
        this.radValueBooleanOk.setChecked(false);
        this.radValueBooleanNotOk.setChecked(false);
        this.chkValueBoolean.clearCheck();
        this.txtValueDecimal.setText(null);
        this.txtValueInteger.setText(null);
        //value type
        if (currentStepObject.getValueType().equals(DomainStepObject.VALUE_TYPE_BOOLEAN)) {
            this.chkValueBoolean.setVisibility(View.VISIBLE);
            this.txtValueDecimal.setVisibility(View.GONE);
            this.txtValueInteger.setVisibility(View.GONE);
        } else if (currentStepObject.getValueType().equals(DomainStepObject.VALUE_TYPE_INTEGER)) {
            this.chkValueBoolean.setVisibility(View.GONE);
            this.txtValueDecimal.setVisibility(View.GONE);
            this.txtValueInteger.setVisibility(View.VISIBLE);
        } else if (currentStepObject.getValueType().equals(DomainStepObject.VALUE_TYPE_DECIMAL)) {
            this.chkValueBoolean.setVisibility(View.GONE);
            this.txtValueDecimal.setVisibility(View.VISIBLE);
            this.txtValueInteger.setVisibility(View.GONE);
        } else if (currentStepObject.getValueType().equals(DomainStepObject.VALUE_TYPE_NONE)) {
            this.chkValueBoolean.setVisibility(View.GONE);
            this.txtValueDecimal.setVisibility(View.GONE);
            this.txtValueInteger.setVisibility(View.GONE);
        } else {
            this.chkValueBoolean.setVisibility(View.GONE);
            this.txtValueDecimal.setVisibility(View.GONE);
            this.txtValueInteger.setVisibility(View.GONE);
        }

        //value hint
//        this.chkValueBoolean.setText(currentStepObject.getValueHint());
        this.txtValueDecimal.setHint(currentStepObject.getValueHint());
        this.txtValueInteger.setHint(currentStepObject.getValueHint());

        this.comment = null;

        //buttons
        if (previousStepId == null || !enableBack) {
            this.btnBack.setVisibility(View.GONE);
        } else {
            this.btnBack.setVisibility(View.VISIBLE);
        }
        if (currentStepObject.isLastStep()) {
            this.btnNext.setVisibility(View.GONE);
            this.btnFinish.setVisibility(View.VISIBLE);
        } else {
            this.btnNext.setVisibility(View.VISIBLE);
            this.btnFinish.setVisibility(View.GONE);
        }

        //skip
        if (currentStepObject.isEnableSkip()) { //skipable
            DialogInterface.OnClickListener skipOnClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //skip perform
                    gotoStep(currentStepObject.getSkipToStep());
                }
            };
            DialogInterface.OnClickListener dontSkipOnClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //map
                    if (currentStepObject.isShowMap()) {
                        Intent intent = new Intent(DomainStepActivity.this, MapActivity.class);
                        intent.putExtra(MapActivity.HEADER_TITLE_KEY, currentStepObject.getTitle());
                        intent.putExtra(MapActivity.MAP_LINK_KEY, currentStepObject.getMapFromPreviousLink());
                        startActivity(intent);
                    }
                }
            };

            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setMessage(getString(R.string.skip_confirmation) + " " + currentStepObject.getTitle() + "?")
                    .setPositiveButton(R.string.dont_skip, dontSkipOnClickListener)
                    .setNegativeButton(R.string.skip, skipOnClickListener)
                    .setCancelable(false)
                    .create();
            alertDialog.show();
        } else {    //not skipable
            //map
            if (currentStepObject.isShowMap()) {
                Intent intent = new Intent(DomainStepActivity.this, MapActivity.class);
                intent.putExtra(MapActivity.HEADER_TITLE_KEY, currentStepObject.getTitle());
                intent.putExtra(MapActivity.MAP_LINK_KEY, currentStepObject.getMapFromPreviousLink());
                startActivity(intent);
            }
        }

//        if (skippedPrevious)
    }

    static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int inSampleSize = 1;    //Default subsampling size
        // See if image raw height and width is bigger than that of required view
        if (options.outHeight > reqHeight || options.outWidth > reqWidth) {
            //bigger
            final int halfHeight = options.outHeight / 2;
            final int halfWidth = options.outWidth / 2;
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    private void hideKeyBoard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View currentFocus = getCurrentFocus();
        if (currentFocus == null) {
            currentFocus = new View(this);
        }
        inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
    }

    private DomainStepObject getDomainStepObject(int id) {
        DomainStepObject domainStepObject = databaseHelper.getDomainStep(id);

        if (domainStepObject == null) {
            throw new NullPointerException("Unable to get domain step for id " + id);
        }

        return domainStepObject;
    }
}
