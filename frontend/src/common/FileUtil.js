export function getFileSize (size) {
  const Unit = ['B', 'KB', 'MB', 'GB', 'TB']
  let i = 0

  function getSize (n) {
    if (n < 1024) {
      return n.toFixed(2) + Unit[i]
    }
    i++
    return getSize(n / 1024)
  }

  return getSize(parseInt(size))
}
