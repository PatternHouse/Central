echo
echo "Stage [3] INFO : Populating static websites ... "
pushd ./Build > /dev/null 2>&1
cp -R ./Sources/Static/* ./Portal/
echo "Stage [3] INFO : Populating static website assets ... "
cp -R ./Assets/* ./Portal/
popd > /dev/null 2>&1
