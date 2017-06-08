# common-dlock
demo

DLock lock = new RedisDLock(host, port, passport);

try{
  lock.lock(key, holder);
  
  ...
  

}finally{
  lock.unlock(key, holder);
}
