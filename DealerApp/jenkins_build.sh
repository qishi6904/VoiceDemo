#!/usr/bin/env bash

## 获取当前脚步所在根目录
this_dir=`pwd`
dirname $0|grep "^/" >/dev/null
if [ $? -eq 0 ];then
this_dir=`dirname $0`
else
dirname $0|grep "^\." >/dev/null
retval=$?
if [ ${retval} -eq 0 ];then
this_dir=`dirname $0|sed "s#^.#$this_dir#"`
else
this_dir=`dirname $0|sed "s#^#$this_dir/#"`
fi
fi
echo ${this_dir}

cd ${this_dir}/

git checkout $2
git pull

echo $1
echo $2

if [ -z $1 ];then
env_name=release
else
env_name=$1
fi

./gradlew clean
./gradlew assemble$env_name

apkFileDir="${this_dir}/app/build/outputs/apk/$env_name/app-$env_name.apk"
dataTime=`date '+%Y-%m-%d %H:%M:%S'`
commitID=`git rev-parse --short HEAD`
echo ${apkFileDir}

if [ "$env_name"x == "pre_production"x ];then
firToken=be0b169577bc0248300397d2ef38f201
elif [ "$env_name"x == "production"x ];then
firToken=293570679240b7dfae5c6cdd2c03ad6b
elif [ "$env_name"x == "production_2"x ];then
firToken=b3c9f32429855dc37721db9f394a92c4
elif [ "$env_name"x == "release"x ];then
firToken=293570679240b7dfae5c6cdd2c03ad6b
elif [ "$env_name"x == "dev"x ];then
firToken=5abd270014d4747c09fe3eccd4e19f49
elif [ "$env_name"x == "qa"x ];then
firToken=5abd270014d4747c09fe3eccd4e19f49
elif [ "$env_name"x == "uat"x ];then
firToken=0ca05fc460d275d04a471c4b33b9d15d
else
firToken=5abd270014d4747c09fe3eccd4e19f49
fi
echo ${firToken}

fir publish ${apkFileDir} --changelog="${dataTime}-${commitID}" --qrcode --token=${firToken}
echo "Generate Signed APK and Upload to Fir SUCCESSFULLY"