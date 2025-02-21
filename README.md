# SplitTheCost
## 目次(Table of Content)  
- [アプリケーションついて](#アプリケーションついて)
- [メイン機能について](#メイン機能について)
- [ビルドまでの流れ](#ビルドまでの流れ)
- [apkファイルのダウンロード](#apkファイルのダウンロード)
- [制作環境](#制作環境)
- [ライセンス](#ライセンス)

　　


## アプリケーションついて
割り勘をした時に1人あたりの金額を計算するアプリです。  
割引が行われた時や、値引きされたり多く支払ったりするメンバーがいる時の計算にも対応しています。　　

　　

##  ビルドまでの流れ  
ローカル環境に本プロジェクトをクローンし、ビルドを実行してください。  

**※ビルドができない場合**
本プロジェクトSplitTheCost直下のSplitTheCost.apkを端末にインストールしてください。(2019/07/28更新分反映)  　
SplitTheCost/SplitTheCost.apk  
最新の状態が反映されていない場合があるため、更新日のご確認をお願いします。  

  　  
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

　　
  
  ## メイン機能について
初回起動時の場合はホーム画面から「設定」画面に遷移します。設定画面では、割り勘の計算時に行う際の「端数の数値」と端数の「処理方法」の設定を行ってください。  
1. 「詳細会計」ボタンをタップする。
1. 「合計金額」に適当な数値を入力する。
1. 「値引き」に適当な数値を入力する。
1. 「参加者登録」ボタンをタップする  
    1. 「新規メンバー追加」ボタンをタップする。
    1. 必要に応じて「新規グループ登録」「新規ポジション登録」ボタンをタップし、属性情報を登録する。
    1. 新規メンバーとして登録する「名前」「グループ」「ポジション」を入力する。
    1. 計算を行いたい人数分、新規メンバー登録を行う。
    1. 端末の「戻る(◁)」ボタンをタップする。
1. 登録されている参加者名左部にのチェックリストをタップする。  
1. 端末の「戻る(◁)」ボタンをタップする。
1. 参加人数：右部の人数が、選択した人数になっていることを確認する。
1. 特別会計から「金額」「円or円引き」を選択する。
1. 入力した行の「適用者選択」ボタンをタップし、計算を適用するメンバー名左部のチェックボックスをタップしてチェックを行う。人数が変化していることを確認する。
1. 「計算」ボタンをタップする。
1. 集金を行ったメンバー名の左部のチェックリストをタップすることで、チェックを行います。
1. 端末の「戻る(◁)」ボタン　又は　「一時保存して戻る」ボタンをタップして、前の画面に戻ります。　　
1. 「一時保存を呼び出す」ボタンが現れていることを確認し、タップします。
1. 先ほどチェックした情報がそのまま呼び出されることを確認します。

以上がメイン機能の流れとなります。  
最終的なチェックリストの左上端をタップすることで、最終的に回収する金額が表示されます。  
※表示される金額は、端数計算処理を行った後の最終的な集計額となるため、入力した合計金額とは異なります。  

メンバー情報やメンバーの属性情報は、ホーム画面の「メンバー管理」より参照・編集・削除が可能です。　　

　　


## 制作環境  
|    |    |
|---:|:---|
|**OS**|Windows 10<br>Ubuntu 18.04.2|  
|**Tool**|Android Studio 3.4.2<br>Compile Sdk Version:28(API:Android9.0(Pie))|  
|**Java**|1.8.0_152|
|**Git**|Git bash<br>Command Line|　　

　　
　　
## ライセンス
[MIT License](https://github.com/koh1project/SplitTheCost/blob/master/LICENSE)
