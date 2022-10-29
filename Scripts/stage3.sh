echo
echo "Stage [3] INFO : Populating static websites ... "
pushd ./Build > /dev/null 2>&1
cp -R ./Sources/StaticPortal/* ./Portal/
echo "Stage [3] INFO : Populating static website assets ... "
cp -R ./Assets/* ./Portal/

echo "Stage [3] INFO : Fetching CDN Assets ... "
git clone https://github.com/openAOD/cdn-o-o.git > /dev/null 2>&1
mkdir ./Assets/patterns

cp -R ./cdn-o-o/assets/img/patterns/* ./Assets/patterns/

popd > /dev/null 2>&1
