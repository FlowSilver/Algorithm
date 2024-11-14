"""
Standard selection sort using list (non-stable)

"""

# bubble sort numbers in increasing order
def selection_sort(lst):
    n = len(lst)
    
    for i in range(n):
        min_idx = i
        for j in range(i+1, n):
            if lst[j] < lst[min_idx]:
                min_idx = j 
        
        swap(lst, i, min_idx)     
   
# swap two element in a list
def swap(lst, i, j):
    tmp = lst[i] 
    lst[i] = lst[j]
    lst[j] = tmp

if __name__ == '__main__':
    the_list = [3,2,43,1,2,4,2,3,4,2,6,87,9,9,34,54,3]
    print(the_list)
    selection_sort(the_list)
    print(the_list)
    


