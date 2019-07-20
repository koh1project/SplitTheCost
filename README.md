# SplitTheCost

## アプリケーションついて
割り勘をした時に1人あたりの金額を計算するアプリです。  
割引が行われた時や、値引きされたり多く支払ったりするメンバーがいる時の計算にも対応しています。

##  ビルドまでの流れ  
ローカル環境に本プロジェクトをクローンし、ビルドを実行してください。  

### プロジェクトのクローン  
`git clone https://github.com/koh1project/SplitTheCost.git`  
`cd SplitTheCost`

### sdkの実行環境確認
環境変数`ANDROIND_HOME`にandroid_sdkのパスが通っていることを確認してください。  

MacOS / LinuxOS の確認方法  
`$ echo $ANDROID_HOME`  

WindowsOSの確認方法  
`$ echo %ANDROID_HOME%`  

パスが通っていない場合は、以下の方法でパスを設定してください。

MacOS / LinuxOS の設定方法  
`$ export ANDROID_HOME=<your/android_sdk/path>`  

WindowsOSの確認方法   
`set ANDROIND_HOME=<your/android_sdk/path>`  

### ビルドの実行
MacOS / LinuxOS のbuild方法  
`./gradlew assembleDebug`  

WindowsOSのbuild方法  
`gradlew.bat assembleDebug`  

## apkファイルのダウンロード
上記のビルドを実行後`/SplitTheCost/app/build/outputs/apk/debug/`に`app-debug.apk`が作成されます。  
作成されたapkファイルをファイル転送やadbコマンドなどで、Android端末にダウンロードしてください。
