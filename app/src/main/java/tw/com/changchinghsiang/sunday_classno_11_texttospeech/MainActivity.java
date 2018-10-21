package tw.com.changchinghsiang.sunday_classno_11_texttospeech;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int REQUESTCODE = 9999;

    private TextView tvShow;
    private EditText etInput;

    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //連結畫面物件
        findViews();
        //檢查文字轉語音功能是否存在
        onCallTextToSpeech();
    }

    //連結畫面物件
    private void findViews() {
        tvShow = (TextView) findViewById(R.id.textView);
        etInput = (EditText) findViewById(R.id.editText);
    }

    //檢查文字轉語音功能是否存在
    private void onCallTextToSpeech() {
        Intent chkIntent = new Intent();
        chkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(chkIntent, REQUESTCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUESTCODE){
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS){
                //文字轉語音功能存在
                tts = new TextToSpeech(this, ttsListener);
                tvShow.setText("Pass");
            }
            else {
                //文字轉語音功能"不"存在，送出安裝意圖
                Intent installIntent = new Intent();
                installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installIntent);
            }
        }
    }

    //文字轉語音監聽器
    private TextToSpeech.OnInitListener ttsListener = new TextToSpeech.OnInitListener() {
        @Override
        public void onInit(int i) {
            //設定檢查指定語言是否可使用
            int status = tts.isLanguageAvailable(Locale.ENGLISH);
            String result = getLangAvailable(status);
            tvShow.setText("Result");
        }
    };

    private String getLangAvailable(int langStatus) {
        String result = "";

        switch(langStatus){
            case TextToSpeech.LANG_AVAILABLE:
                //可使用
                result = "LANG_AVAILABLE";
                break;

            case TextToSpeech.LANG_MISSING_DATA:
                //缺少語音數據檔案（可能被誤刪）
                result = "LANG_MISSING_DATA";
                break;

            case TextToSpeech.LANG_NOT_SUPPORTED:
                //不支援
                result = "LANG_NOT_SUPPORTED";
                break;

            default:
                //發生不可預期的情況
                result = "Error: Could not Happen";
        }

        return result;
    }

    //button:ENGLISH
    public void englishOnClick(View view) {
        String content = etInput.getText().toString();

        content = "站在這深邃無際的森林 伴隨我最愛的淡淡香氣 曾經用盡全力去尋找歲月的印記 不經意碰觸你的痕跡 " +
                "伸出手想要感受你 曾經給過的溫柔 無法挽留 那份幸福不再有 好想念你 在都市的鋼鐵叢林 " +
                "每一個輾轉難眠的夜裡 我只能想念你 熟悉聲音你殘留下的光與影 彷彿把我包圍在你懷裡 " +
                "靜靜擁抱我默默的哭泣 夕陽抹下的身影 就像是軟弱無力的自己 無法去跨越面前這座愛情的廢墟 " +
                "只留下點點殷紅血跡 無奈的伸出雙手 想要擁住你的溫柔 卻只感受冰冷溫度在胸口 永遠愛你這份承諾過的往昔 " +
                "早已消失在遙遠的森林 隨著狂風遠去 那些曾經 就算用時間來銘記 也只留住片片殘缺回憶 " +
                "難道注定是我們的結局 我的淚滴 化作記憶倒 映出殘留的勇氣 無法平息 永遠留在心底 " +
                "無法抹去曾經留給我的回憶 你的眼睛你的呼吸 如今還牽動我的心 最難放棄定格在過去的身影 " +
                "帶我回到你說愛我的那一句 感受著你永遠不再分離 在曾經有你的椮椮森林";

        if (!content.equals("")){
            //設定語言
            tts.setLanguage(Locale.ENGLISH);
            //設定音高
            tts.setPitch(1.0f);
            //設定語速
            tts.setSpeechRate(1.0f);
            //文字轉語音：說
            tts.speak(content, TextToSpeech.QUEUE_ADD, null, null);
        }
    }

    //button:
    public void chineseOnClick(View view) {
        String content = etInput.getText().toString();

        if (!content.equals("")){
            tts.setLanguage(Locale.CHINESE);
            tts.setPitch(1.0f);
            tts.setSpeechRate(1.0f);
            tts.speak(content, TextToSpeech.QUEUE_ADD, null, null);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(tts!=null){
            tts.stop();         //停止
            tts.shutdown();     //關閉
        }
    }
}
