export const applyOrderStatusBadge = (status: string): string => {
  switch (status) {
    case 'pending':
      return 'border-yellow-500 bg-yellow-100 text-yellow-600'
    case 'confirmed':
      return 'border-green-500 bg-green-100 text-green-600'
    case 'payment_failed':
      return 'border-red-500 bg-red-100 text-red-600'
    case 'processing':
      return 'border-blue-500 bg-blue-100 text-blue-600'
    case 'shipped':
      return 'border-indigo-500 bg-indigo-100 text-indigo-600'
    case 'delivered':
      return 'border-teal-500 bg-teal-100 text-teal-600'
    case 'canceled':
      return 'border-gray-500 bg-gray-100 text-gray-600'
    default:
      return 'border-gray-300 bg-gray-50 text-gray-400'
  }
}
